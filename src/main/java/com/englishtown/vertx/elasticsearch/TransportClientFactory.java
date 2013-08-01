package com.englishtown.vertx.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;

/**
 * Factory to create an elastic search TransportClient
 */
public interface TransportClientFactory {

    /**
     * Create a client from the settings
     *
     * @param settings the settings used to create the client
     * @return
     */
    TransportClient create(Settings settings);

}
