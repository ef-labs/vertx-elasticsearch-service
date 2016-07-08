package com.englishtown.vertx.elasticsearch.integration;

/**
 * "service" prefix integration tests
 */
public class ServiceIntegrationTest extends IntegrationTestBase {
    @Override
    protected void deployVerticle() throws Exception {
        deployVerticle("service:com.englishtown.vertx.vertx-elasticsearch-service");
    }
}
