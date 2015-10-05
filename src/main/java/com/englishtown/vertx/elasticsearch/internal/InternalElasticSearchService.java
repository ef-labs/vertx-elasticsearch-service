package com.englishtown.vertx.elasticsearch.internal;

import com.englishtown.vertx.elasticsearch.ElasticSearchService;
import org.elasticsearch.client.transport.TransportClient;

/**
 * Internal
 */
public interface InternalElasticSearchService extends ElasticSearchService {

    /**
     * Return the inner {@link TransportClient}
     */
    TransportClient getClient();

}
