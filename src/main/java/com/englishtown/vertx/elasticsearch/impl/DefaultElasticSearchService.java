package com.englishtown.vertx.elasticsearch.impl;

import com.englishtown.vertx.elasticsearch.ElasticSearchConfigurator;
import com.englishtown.vertx.elasticsearch.ElasticSearchService;
import com.englishtown.vertx.elasticsearch.SearchOptions;
import com.englishtown.vertx.elasticsearch.TransportClientFactory;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Default implementation of {@link com.englishtown.vertx.elasticsearch.ElasticSearchService}
 */
public class DefaultElasticSearchService implements ElasticSearchService {

    private final TransportClientFactory clientFactory;
    private final ElasticSearchConfigurator configurator;
    protected TransportClient client;

    public static final String CONST_ID = "id";
    public static final String CONST_INDEX = "index";
    public static final String CONST_TYPE = "type";
    public static final String CONST_VERSION = "version";
    public static final String CONST_SOURCE = "source";

    public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    @Inject
    public DefaultElasticSearchService(TransportClientFactory clientFactory, ElasticSearchConfigurator configurator) {
        this.clientFactory = clientFactory;
        this.configurator = configurator;
    }

    @Override
    public void start() {

        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", configurator.getClusterName())
                .put("client.transport.sniff", configurator.getClientTransportSniff())
                .build();

        client = clientFactory.create(settings);
        configurator.getTransportAddresses().forEach(client::addTransportAddress);

    }

    @Override
    public void stop() {
        client.close();
        client = null;
    }

    @Override
    public void index(String index, String type, String id, JsonObject source, Handler<AsyncResult<JsonObject>> resultHandler) {

        client.prepareIndex(index, type, id)
                .setSource(source.encode())
                .execute(new ActionListener<IndexResponse>() {
                    @Override
                    public void onResponse(IndexResponse indexResponse) {
                        JsonObject result = new JsonObject()
                                .put(CONST_INDEX, indexResponse.getIndex())
                                .put(CONST_TYPE, indexResponse.getType())
                                .put(CONST_ID, indexResponse.getId())
                                .put(CONST_VERSION, indexResponse.getVersion());
                        resultHandler.handle(Future.succeededFuture(result));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        resultHandler.handle(Future.failedFuture(t));
                    }
                });

    }

    @Override
    public void get(String index, String type, String id, Handler<AsyncResult<JsonObject>> resultHandler) {

        client.prepareGet(index, type, id)
                .execute(new ActionListener<GetResponse>() {
                    @Override
                    public void onResponse(GetResponse getResponse) {
                        JsonObject source = (getResponse.isExists() ? new JsonObject(getResponse.getSourceAsString()) : null);
                        JsonObject reply = new JsonObject()
                                .put(CONST_INDEX, getResponse.getIndex())
                                .put(CONST_TYPE, getResponse.getType())
                                .put(CONST_ID, getResponse.getId())
                                .put(CONST_VERSION, getResponse.getVersion())
                                .put(CONST_SOURCE, source);
                        resultHandler.handle(Future.succeededFuture(reply));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        resultHandler.handle(Future.failedFuture(t));
                    }
                });

    }

    @Override
    public void search(List<String> indices, SearchOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {

        SearchRequestBuilder builder = client.prepareSearch(indices.toArray(new String[indices.size()]));

        if (options != null) {

            // Set types
            if (!options.getTypes().isEmpty()) {
                builder.setTypes(options.getTypes().toArray(new String[options.getTypes().size()]));
            }

            // Set the query
            if (options.getQuery() != null) {
                builder.setQuery(options.getQuery().encode());
            }

            // Set the filter
            if (options.getFilter() != null) {
                builder.setPostFilter(options.getFilter().encode());
            }

            // Set facets
            if (options.getFacets() != null) {
                builder.setFacets(options.getFacets().encode().getBytes(CHARSET_UTF8));
            }

            // Set search type
            if (options.getSearchType() != null) {
                builder.setSearchType(options.getSearchType());
            }

            // Set scroll keep alive time
            if (options.getScroll() != null) {
                builder.setScroll(options.getScroll());
            }

            // Set Size
            if (options.getSize() != null) {
                builder.setSize(options.getSize());
            }

            // Set From
            if (options.getFrom() != null) {
                builder.setFrom(options.getFrom());
            }

            // Set requested fields
            if (!options.getFields().isEmpty()) {
                options.getFields().forEach(builder::addField);
            }

            // Set query timeout
            if (options.getTimeout() != null) {
                builder.setTimeout(new TimeValue(options.getTimeout()));
            }

            // Add sorts
            if (!options.getSorts().isEmpty()) {
                options.getSorts().forEach(sort -> builder.addSort(sort.getField(), sort.getOrder()));
            }

        }

        builder.execute(new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                JsonObject json = readResponse(searchResponse);
                resultHandler.handle(Future.succeededFuture(json));
            }

            @Override
            public void onFailure(Throwable t) {
                resultHandler.handle(Future.failedFuture(t));
            }
        });
    }

    @Override
    public void scroll(String scrollId, String scroll, Handler<AsyncResult<JsonObject>> resultHandler) {

        client.prepareSearchScroll(scrollId)
                .setScroll(scroll)
                .execute(new ActionListener<SearchResponse>() {
                    @Override
                    public void onResponse(SearchResponse searchResponse) {
                        JsonObject json = readResponse(searchResponse);
                        resultHandler.handle(Future.succeededFuture(json));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        resultHandler.handle(Future.failedFuture(t));
                    }
                });

    }

    @Override
    public void delete(String index, String type, String id, Handler<AsyncResult<JsonObject>> resultHandler) {

        client.prepareDelete(index, type, id)
                .execute(new ActionListener<DeleteResponse>() {
                    @Override
                    public void onResponse(DeleteResponse deleteResponse) {
                        JsonObject json = new JsonObject()
                                .put(CONST_INDEX, deleteResponse.getIndex())
                                .put(CONST_TYPE, deleteResponse.getType())
                                .put(CONST_ID, deleteResponse.getId())
                                .put(CONST_VERSION, deleteResponse.getVersion());
                        resultHandler.handle(Future.succeededFuture(json));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        resultHandler.handle(Future.failedFuture(t));
                    }
                });

    }

    protected JsonObject readResponse(ToXContent toXContent) {

        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            toXContent.toXContent(builder, SearchResponse.EMPTY_PARAMS);
            builder.endObject();

            return new JsonObject(builder.string());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
