package com.englishtown.vertx.elasticsearch.impl;

import com.englishtown.vertx.elasticsearch.ElasticSearchAdminService;
import com.englishtown.vertx.elasticsearch.MappingOptions;
import com.englishtown.vertx.elasticsearch.internal.InternalElasticSearchAdminService;
import com.englishtown.vertx.elasticsearch.internal.InternalElasticSearchService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.AdminClient;

import javax.inject.Inject;
import java.util.List;

/**
 * Default implementation of {@link ElasticSearchAdminService}
 */
public class DefaultElasticSearchAdminService implements InternalElasticSearchAdminService {

    private final InternalElasticSearchService service;

    @Inject
    public DefaultElasticSearchAdminService(InternalElasticSearchService service) {
        this.service = service;
    }

    @Override
    public void putMapping(List<String> indices, String type, JsonObject source, MappingOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {

        PutMappingRequestBuilder builder = getAdmin().indices()
                .preparePutMapping(indices.toArray(new String[indices.size()]))
                .setType(type)
                .setSource(source.encode());

        if (options != null) {
            if (options.shouldIgnoreConflicts() != null) builder.setIgnoreConflicts(options.shouldIgnoreConflicts());
        }

        builder.execute(new ActionListener<PutMappingResponse>() {
            @Override
            public void onResponse(PutMappingResponse putMappingResponse) {
                JsonObject json = new JsonObject()
                        .put("acknowledged", putMappingResponse.isAcknowledged());
                resultHandler.handle(Future.succeededFuture(json));
            }

            @Override
            public void onFailure(Throwable e) {
                resultHandler.handle(Future.failedFuture(e));
            }
        });

    }

    /**
     * Returns the inner admin client
     *
     * @return
     */
    @Override
    public AdminClient getAdmin() {
        return service.getClient().admin();
    }

}
