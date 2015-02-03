package com.englishtown.vertx.elasticsearch;

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ProxyHelper;

import javax.inject.Inject;

/**
 * ElasticSearch event bus service verticle
 */
public class ElasticSearchServiceVerticle extends AbstractVerticle {

    private final ElasticSearchService service;

    @Inject
    public ElasticSearchServiceVerticle(ElasticSearchService service) {
        this.service = service;
    }

    @Override
    public void start() throws Exception {

        String address = config().getString("address");
        if (address == null || address.isEmpty()) {
            throw new IllegalStateException("address field must be specified in config for service verticle");
        }

        // Register service as an event bus proxy
        ProxyHelper.registerService(ElasticSearchService.class, vertx, service, address);

        // Start the service
        service.start();

    }

    @Override
    public void stop() throws Exception {
        service.stop();
    }

}
