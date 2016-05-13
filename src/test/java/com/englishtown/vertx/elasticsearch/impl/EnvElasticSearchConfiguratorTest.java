package com.englishtown.vertx.elasticsearch.impl;

import io.vertx.core.json.JsonObject;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.junit.Test;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class EnvElasticSearchConfiguratorTest {

    private final String ENV_TRANSPORT_ADDRESS = EnvElasticSearchConfigurator.ENV_VAR_TRANSPORT_ADDRESSES;

    @Test
    public void testInitTransportAddressesWhenSingleNoPort() throws Exception {
        setEnv(Collections.singletonMap(ENV_TRANSPORT_ADDRESS, "172.45.0.1"));

        final EnvElasticSearchConfigurator envConfigurator = new EnvElasticSearchConfigurator(new JsonObject());

        final List<TransportAddress> expected =
                Collections.singletonList(new InetSocketTransportAddress(new InetSocketAddress("172.45.0.1", 9300)));

        final List<TransportAddress> actual = envConfigurator.transportAddresses;

        assertEquals(expected, actual);
    }

    @Test
    public void testInitTransportAddressesWhenSingleHasPort() throws Exception {
        setEnv(Collections.singletonMap(ENV_TRANSPORT_ADDRESS, "172.45.0.1:9305"));

        final EnvElasticSearchConfigurator envConfigurator = new EnvElasticSearchConfigurator(new JsonObject());

        final List<TransportAddress> expected =
                Collections.singletonList(new InetSocketTransportAddress(new InetSocketAddress("172.45.0.1", 9305)));

        final List<TransportAddress> actual = envConfigurator.transportAddresses;

        assertEquals(expected, actual);
    }

    @Test
    public void testInitTransportAddressesWhenMultipleNoPort() throws Exception {
        setEnv(Collections.singletonMap(ENV_TRANSPORT_ADDRESS, "172.45.0.1|172.45.0.2"));

        final EnvElasticSearchConfigurator envConfigurator = new EnvElasticSearchConfigurator(new JsonObject());

        final List<TransportAddress> expected = new LinkedList<>();
        expected.add(new InetSocketTransportAddress(new InetSocketAddress("172.45.0.1", 9300)));
        expected.add(new InetSocketTransportAddress(new InetSocketAddress("172.45.0.2", 9300)));

        final List<TransportAddress> actual = envConfigurator.transportAddresses;

        assertEquals(expected, actual);
    }

    @Test
    public void testInitTransportAddressesWhenMultipleHasPort() throws Exception {
        setEnv(Collections.singletonMap(ENV_TRANSPORT_ADDRESS, "172.45.0.1:9305|172.45.0.2:9306"));

        final EnvElasticSearchConfigurator envConfigurator = new EnvElasticSearchConfigurator(new JsonObject());

        final List<TransportAddress> expected = new LinkedList<>();
        expected.add(new InetSocketTransportAddress(new InetSocketAddress("172.45.0.1", 9305)));
        expected.add(new InetSocketTransportAddress(new InetSocketAddress("172.45.0.2", 9306)));

        final List<TransportAddress> actual = envConfigurator.transportAddresses;

        assertEquals(expected, actual);
    }

    private void setEnv(Map<String, String> newenv) throws Exception {
        final Class[] classes = Collections.class.getDeclaredClasses();
        final Map<String, String> env = System.getenv();

        Arrays.asList(classes).stream()
                .filter(c -> "java.util.Collections$UnmodifiableMap".equals(c.getName()))
                .forEach(c -> newEnvForClass(c, env, newenv));
    }

    private void newEnvForClass(Class c, Map<String, String> env, Map<String, String> newenv) {
        try {
            final Field field = c.getDeclaredField("m");
            field.setAccessible(true);
            final Map<String, String> map = (Map<String, String>) field.get(env);
            map.clear();
            map.putAll(newenv);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update ENV", e);
        }
    }
}