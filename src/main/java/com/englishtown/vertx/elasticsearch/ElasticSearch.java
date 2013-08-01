package com.englishtown.vertx.elasticsearch;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import javax.inject.Inject;

/**
 * ElasticSearch event bus verticle
 */
public class ElasticSearch extends BusModBase implements Handler<Message<JsonObject>> {

    protected final TransportClientFactory clientFactory;
    protected TransportClient client;
    protected String address;

    public static final String CONFIG_TRANSPORT_ADDRESSES = "transportAddresses";
    public static final String CONFIG_HOSTNAME = "hostname";
    public static final String CONFIG_PORT = "port";
    public static final String CONFIG_ADDRESS = "address";
    public static final String DEFAULT_ADDRESS = "et.vertx.elasticsearch";

    @Inject
    public ElasticSearch(TransportClientFactory clientFactory) {
        if (clientFactory == null) {
            throw new IllegalArgumentException("clientProvider is null");
        }
        this.clientFactory = clientFactory;
    }

    /**
     * Start the busmod
     */
    @Override
    public void start() {
        super.start();

        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", config.getString("cluster_name", "elasticsearch"))
                .put("client.transport.sniff", config.getBoolean("client_transport_sniff", true))
                .build();

        client = clientFactory.create(settings);

        JsonArray transportAddresses = config.getArray(CONFIG_TRANSPORT_ADDRESSES);
        if (transportAddresses != null) {
            for (int i = 0; i < transportAddresses.size(); i++) {
                JsonObject transportAddress = transportAddresses.get(i);
                String hostname = transportAddress.getString(CONFIG_HOSTNAME);

                if (hostname != null && !hostname.isEmpty()) {
                    int port = transportAddress.getInteger(CONFIG_PORT, 9300);
                    client.addTransportAddress(new InetSocketTransportAddress(hostname, port));
                }
            }
        }

        // If no addresses are configured, add local host on the default port
        if (client.transportAddresses().size() == 0) {
            client.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        }

        address = config.getString(CONFIG_ADDRESS, DEFAULT_ADDRESS);
        eb.registerHandler(address, this);
    }

    /**
     * Handle an incoming elastic search message
     */
    @Override
    public void handle(Message<JsonObject> message) {

        try {
            String action = getMandatoryString("action", message);
            if (action == null) {
                return;
            }

            switch (action) {
                case "index":
                    doIndex(message);
                    break;
                case "get":
                    doGet(message);
                    break;
                default:
                    sendError(message, "Unrecognized action " + action);
                    break;
            }

        } catch (Exception e) {
            sendError(message, "Unhandled exception!", e);
        }

    }

    /**
     * See http://www.elasticsearch.org/guide/reference/api/index_/
     *
     * @param message
     */
    public void doIndex(final Message<JsonObject> message) {

        JsonObject body = message.body();

        final String index = getIndex(body, message);
        if (index == null) {
            return;
        }

        String type = getType(body, message);
        if (type == null) {
            return;
        }

        JsonObject source = body.getObject("source");
        if (source == null) {
            sendError(message, "source is required");
            return;
        }

        // id is optional
        String id = body.getString("id");

        client.prepareIndex(index, type, id)
                .setSource(source.encode())
                .execute(new ActionListener<IndexResponse>() {
                    @Override
                    public void onResponse(IndexResponse indexResponse) {
                        JsonObject reply = new JsonObject()
                                .putString("index", indexResponse.getIndex())
                                .putString("type", indexResponse.getType())
                                .putString("id", indexResponse.getId())
                                .putNumber("version", indexResponse.getVersion());
                        sendOK(message, reply);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        sendError(message, "Index error: " + e.getMessage(), new RuntimeException(e));
                    }
                });

    }

    /**
     * http://www.elasticsearch.org/guide/reference/java-api/get/
     *
     * @param message
     */
    public void doGet(final Message<JsonObject> message) {

        JsonObject body = message.body();

        final String index = getIndex(body, message);
        if (index == null) {
            return;
        }

        String type = getType(body, message);
        if (type == null) {
            return;
        }

        String id = body.getString("id");
        if (id == null) {
            sendError(message, "id is required");
            return;
        }

        client.prepareGet(index, type, id)
                .execute(new ActionListener<GetResponse>() {
                    @Override
                    public void onResponse(GetResponse getFields) {
                        JsonObject source = (getFields.isExists() ? new JsonObject(getFields.getSourceAsMap()) : null);
                        JsonObject reply = new JsonObject()
                                .putString("index", getFields.getIndex())
                                .putString("type", getFields.getType())
                                .putString("id", getFields.getId())
                                .putNumber("version", getFields.getVersion())
                                .putObject("source", source);
                        sendOK(message, reply);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        sendError(message, "Get error: " + e.getMessage(), new RuntimeException(e));
                    }
                });

    }

    protected String getIndex(JsonObject json, Message<JsonObject> message) {
        String index = json.getString("index");
        if (index == null || index.isEmpty()) {
            sendError(message, "index is required");
            return null;
        }
        return index;
    }

    protected String getType(JsonObject json, Message<JsonObject> message) {
        String type = json.getString("type");
        if (type == null || type.isEmpty()) {
            sendError(message, "type is required");
            return null;
        }
        return type;
    }

}
