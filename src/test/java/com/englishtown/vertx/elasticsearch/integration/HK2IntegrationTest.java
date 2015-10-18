package com.englishtown.vertx.elasticsearch.integration;

import io.vertx.core.json.JsonArray;

/**
 * HK2 integration tests
 */
public class HK2IntegrationTest extends IntegrationTestBase {
    @Override
    protected void deployVerticle() throws Exception {
        config.put("address", "et.elasticsearch")
                .put("hk2_binder", new JsonArray()
                        .add("com.englishtown.vertx.hk2.ElasticSearchBinder"));

        deployVerticle("java-hk2:com.englishtown.vertx.elasticsearch.ElasticSearchServiceVerticle");
    }
}
