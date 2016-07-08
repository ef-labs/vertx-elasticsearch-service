package com.englishtown.vertx.elasticsearch.hk2;

import com.englishtown.vertx.elasticsearch.ElasticSearchAdminService;
import com.englishtown.vertx.elasticsearch.ElasticSearchConfigurator;
import com.englishtown.vertx.elasticsearch.ElasticSearchService;
import com.englishtown.vertx.elasticsearch.TransportClientFactory;
import com.englishtown.vertx.elasticsearch.impl.DefaultElasticSearchAdminService;
import com.englishtown.vertx.elasticsearch.impl.DefaultElasticSearchService;
import com.englishtown.vertx.elasticsearch.impl.DefaultTransportClientFactory;
import com.englishtown.vertx.elasticsearch.impl.EnvElasticSearchConfigurator;
import com.englishtown.vertx.elasticsearch.internal.InternalElasticSearchAdminService;
import com.englishtown.vertx.elasticsearch.internal.InternalElasticSearchService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * ElasticSearch HK2 Binder
 */
public class ElasticSearchBinder extends AbstractBinder {
    /**
     * Implement to provide binding definitions using the exposed binding
     * methods.
     */
    @Override
    protected void configure() {

        bind(DefaultTransportClientFactory.class).to(TransportClientFactory.class);
        bind(EnvElasticSearchConfigurator.class).to(ElasticSearchConfigurator.class);
        bind(DefaultElasticSearchService.class).to(InternalElasticSearchService.class).to(ElasticSearchService.class).in(Singleton.class);
        bind(DefaultElasticSearchAdminService.class).to(InternalElasticSearchAdminService.class).to(ElasticSearchAdminService.class).in(Singleton.class);

    }
}
