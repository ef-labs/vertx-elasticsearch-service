package com.englishtown.vertx.hk2;

import com.englishtown.vertx.elasticsearch.ElasticSearchConfigurator;
import com.englishtown.vertx.elasticsearch.ElasticSearchService;
import com.englishtown.vertx.elasticsearch.TransportClientFactory;
import com.englishtown.vertx.elasticsearch.impl.DefaultElasticSearchService;
import com.englishtown.vertx.elasticsearch.impl.DefaultTransportClientFactory;
import com.englishtown.vertx.elasticsearch.impl.EnvElasticSearchConfigurator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

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
        bind(DefaultElasticSearchService.class).to(ElasticSearchService.class);

    }
}
