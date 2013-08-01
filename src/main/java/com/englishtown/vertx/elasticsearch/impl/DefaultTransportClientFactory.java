package com.englishtown.vertx.elasticsearch.impl;

import com.englishtown.vertx.elasticsearch.TransportClientFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;

/**
 * Default implementation of {@link TransportClientFactory}
 */
public class DefaultTransportClientFactory implements TransportClientFactory {
    /**
     * Create a client from the settings
     *
     * @param settings the settings used to create the client
     * @return
     */
    @Override
    public TransportClient create(Settings settings) {
        return new TransportClient(settings);
    }
}
