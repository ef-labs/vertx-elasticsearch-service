package com.englishtown.vertx.elasticsearch.guice;

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
import com.google.inject.AbstractModule;

import javax.inject.Singleton;

/**
 * ElasticSearch Guice Binder
 */
public class ElasticSearchBinder extends AbstractModule {
    /**
     * Implement to provide binding definitions using the exposed binding
     * methods.
     */
    @Override
    protected void configure() {

        bind(TransportClientFactory.class).to(DefaultTransportClientFactory.class);
        bind(ElasticSearchConfigurator.class).to(EnvElasticSearchConfigurator.class);

        bind(DefaultElasticSearchService.class).in(Singleton.class);
        bind(DefaultElasticSearchAdminService.class).in(Singleton.class);

        bind(ElasticSearchService.class).to(DefaultElasticSearchService.class);
        bind(InternalElasticSearchService.class).to(DefaultElasticSearchService.class);

        bind(ElasticSearchAdminService.class).to(DefaultElasticSearchAdminService.class);
        bind(InternalElasticSearchAdminService.class).to(DefaultElasticSearchAdminService.class);

    }
}
