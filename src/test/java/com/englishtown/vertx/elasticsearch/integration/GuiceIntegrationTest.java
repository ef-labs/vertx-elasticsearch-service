package com.englishtown.vertx.elasticsearch.integration;

import io.vertx.core.json.JsonArray;

/**
 * Guice integration tests
 */
public class GuiceIntegrationTest extends IntegrationTestBase {
    @Override
    protected void deployVerticle() throws Exception {
        config.put("address", "et.elasticsearch")
                .put("guice_binder", new JsonArray()
                        .add("com.englishtown.vertx.elasticsearch.guice.ElasticSearchBinder"));

        deployVerticle("java-guice:com.englishtown.vertx.elasticsearch.ElasticSearchServiceVerticle");
    }
}
