package com.englishtown.vertx.elasticsearch;

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ProxyHelper;

import javax.inject.Inject;

/**
 * ElasticSearch event bus service verticle
 */
public class ElasticSearchServiceVerticle extends AbstractVerticle {

    private final ElasticSearchService service;
    private final ElasticSearchAdminService adminService;

    @Inject
    public ElasticSearchServiceVerticle(ElasticSearchService service, ElasticSearchAdminService adminService) {
        this.service = service;
        this.adminService = adminService;
    }

    @Override
    public void start() throws Exception {

        String address = config().getString("address");
        if (address == null || address.isEmpty()) {
            throw new IllegalStateException("address field must be specified in config for service verticle");
        }
        String adminAddress = config().getString("address.admin");
        if (adminAddress == null || adminAddress.isEmpty()) {
            adminAddress = address + ".admin";
        }

        // Register service as an event bus proxy
        ProxyHelper.registerService(ElasticSearchService.class, vertx, service, address);
        ProxyHelper.registerService(ElasticSearchAdminService.class, vertx, adminService, adminAddress);

        // Start the service
        service.start();

    }

    @Override
    public void stop() throws Exception {
        service.stop();
    }

}
