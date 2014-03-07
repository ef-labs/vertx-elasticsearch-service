package com.englishtown.vertx.elasticsearch;

import org.elasticsearch.common.transport.TransportAddress;

import java.util.List;

/**
 * ES client configuration provider
 */
public interface ElasticSearchConfigurator {

    String getClusterName();

    boolean getClientTransportSniff();

    List<TransportAddress> getTransportAddresses();

}
