package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

import java.util.Arrays;
import java.util.List;

/**
 * ElasticSearch service
 */
@VertxGen
@ProxyGen
public interface ElasticSearchService {

    static ElasticSearchService createEventBusProxy(Vertx vertx, String address) {
        return ProxyHelper.createProxy(ElasticSearchService.class, vertx, address);
    }

    @ProxyIgnore
    void start();

    @ProxyIgnore
    void stop();

    default void index(String index, JsonObject source, Handler<AsyncResult<JsonObject>> resultHandler) {
        index(index, null, source, resultHandler);
    }

    default void index(String index, String type, JsonObject source, Handler<AsyncResult<JsonObject>> resultHandler) {
        index(index, type, null, source, resultHandler);
    }

    void index(String index, String type, String id, JsonObject source, Handler<AsyncResult<JsonObject>> resultHandler);

    default void get(String index, String id, Handler<AsyncResult<JsonObject>> resultHandler) {
        get(index, id, null, resultHandler);
    }

    void get(String index, String type, String id, Handler<AsyncResult<JsonObject>> resultHandler);

    default void search(String index, Handler<AsyncResult<JsonObject>> resultHandler) {
        search(index, null, resultHandler);
    }

    default void search(String index, SearchOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {
        search(Arrays.asList(index), options, resultHandler);
    }

    default void search(List<String> indices, Handler<AsyncResult<JsonObject>> resultHandler) {
        search(indices, null, resultHandler);
    }

    void search(List<String> indices, SearchOptions options, Handler<AsyncResult<JsonObject>> resultHandler);

    void scroll(String scrollId, String scroll, Handler<AsyncResult<JsonObject>> resultHandler);

    void delete(String index, String type, String id, Handler<AsyncResult<JsonObject>> resultHandler);

}
