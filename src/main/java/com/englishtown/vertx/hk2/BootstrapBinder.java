package com.englishtown.vertx.hk2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * HK2 bootstrap binder
 */
public class BootstrapBinder extends AbstractBinder {

    /**
     * Implement to provide binding definitions using the exposed binding
     * methods.
     */
    @Override
    protected void configure() {
        install(new com.englishtown.vertx.elasticsearch.hk2.ElasticSearchBinder());
    }

}
