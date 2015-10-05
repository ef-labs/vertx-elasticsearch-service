package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

import java.util.Collections;
import java.util.List;

/**
 * Admin service
 */
@VertxGen
@ProxyGen
public interface ElasticSearchAdminService {

    static ElasticSearchAdminService createEventBusProxy(Vertx vertx, String address) {
        return ProxyHelper.createProxy(ElasticSearchAdminService.class, vertx, address);
    }

    default void putMapping(String index, String type, JsonObject source, Handler<AsyncResult<JsonObject>> resultHandler) {
        putMapping(Collections.singletonList(index), type, source, resultHandler);
    }

    default void putMapping(String index, String type, JsonObject source, MappingOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {
        putMapping(Collections.singletonList(index), type, source, options, resultHandler);
    }

    default void putMapping(List<String> indices, String type, JsonObject source, Handler<AsyncResult<JsonObject>> resultHandler) {
        putMapping(indices, type, source, null, resultHandler);
    }

    void putMapping(List<String> indices, String type, JsonObject source, MappingOptions options, Handler<AsyncResult<JsonObject>> resultHandler);

}
