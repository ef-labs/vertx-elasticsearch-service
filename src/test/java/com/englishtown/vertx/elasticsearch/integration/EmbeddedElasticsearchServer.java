package com.englishtown.vertx.elasticsearch.integration;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class EmbeddedElasticsearchServer implements AutoCloseable {

    private final Node node;

    public static final String DEFAULT_HOME_DIR = "target/elasticsearch";
    public static final int DEFAULT_TCP_PORT = 19300;

    public EmbeddedElasticsearchServer() {
        this(DEFAULT_HOME_DIR, DEFAULT_TCP_PORT);
    }

    public EmbeddedElasticsearchServer(String homeDir, int port) {

        Settings.Builder builder = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch")
                .put("transport.tcp.port", port)
                .put("http.enabled", "false")
                .put("path.home", homeDir);

        node = nodeBuilder()
                .settings(builder.build())
                .node();
    }

    public Client getClient() {
        return node.client();
    }

    public ClusterHealthResponse waitForYellowStatus() {
        return getClient()
                .admin()
                .cluster()
                .prepareHealth()
                .setWaitForYellowStatus()
                .execute()
                .actionGet();
    }

    @Override
    public void close() throws Exception {
        if (node != null) {
            node.close();
        }
    }
}