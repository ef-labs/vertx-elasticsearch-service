package com.englishtown.vertx.elasticsearch.impl;

import io.vertx.core.Vertx;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import io.vertx.core.json.JsonObject;

import javax.inject.Inject;
import java.net.InetSocketAddress;

/**
 * ElasticSearch configuration is read from JSON, but also tries to read environment variables.
 * <p>
 * Environment variables include:
 * ES_CLUSTER_NAME - the ES cluster name to connect to
 * ES_TRANSPORT_ADDRESSES - the ES nodes to connect to in the form hostname:port|hostname:port
 */
public class EnvElasticSearchConfigurator extends JsonElasticSearchConfigurator {

    public static final String ENV_VAR_CLUSTER_NAME = "ES_CLUSTER_NAME";
    public static final String ENV_VAR_TRANSPORT_ADDRESSES = "ES_TRANSPORT_ADDRESSES";

    @Inject
    public EnvElasticSearchConfigurator(Vertx vertx) {
        super(vertx);
    }

    public EnvElasticSearchConfigurator(JsonObject config) {
        super(config);
    }

    @Override
    protected void initClusterName(JsonObject config) {
        clusterName = System.getenv(ENV_VAR_CLUSTER_NAME);

        // Recall super if cluster name env var is missing
        if (clusterName == null) {
            super.initClusterName(config);
        }
    }

    @Override
    protected void initTransportAddresses(JsonObject config) {

        String val = System.getenv(ENV_VAR_TRANSPORT_ADDRESSES);

        if (val != null) {
            String[] addresses = val.split("\\|");
            for (String address : addresses) {
                String[] split = address.split(":");

                String hostname = split[0];
                int port = (split.length == 1 ? 9300 : Integer.parseInt(split[1]));

                transportAddresses.add(new InetSocketTransportAddress(new InetSocketAddress(hostname, port)));
            }
        }

        // Recall super method to get any additional addresses
        super.initTransportAddresses(config);

    }
}
