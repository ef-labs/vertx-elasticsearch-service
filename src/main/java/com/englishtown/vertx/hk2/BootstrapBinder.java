package com.englishtown.vertx.hk2;

import com.englishtown.vertx.elasticsearch.TransportClientFactory;
import com.englishtown.vertx.elasticsearch.impl.DefaultTransportClientFactory;
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

        bind(DefaultTransportClientFactory.class).to(TransportClientFactory.class);

    }

}
