package com.englishtown.vertx.elasticsearch.internal;

import com.englishtown.vertx.elasticsearch.ElasticSearchAdminService;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;
import org.elasticsearch.client.AdminClient;

import java.util.Collections;
import java.util.List;

/**
 * Internal admin service
 */
public interface InternalElasticSearchAdminService extends ElasticSearchAdminService {

    /**
     * Returns the inner admin client
     *
     * @return
     */
    @ProxyIgnore
    AdminClient getAdmin();
}
