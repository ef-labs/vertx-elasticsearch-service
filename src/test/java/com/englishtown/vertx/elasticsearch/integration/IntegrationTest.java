package com.englishtown.vertx.elasticsearch.integration;

import com.englishtown.vertx.elasticsearch.ElasticSearch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;

import static org.vertx.testtools.VertxAssert.*;

/**
 * {@link ElasticSearch} integration test
 */
@RunWith(CPJavaClassRunner.class)
public class IntegrationTest extends TestVerticle {

    private String id = "integration-test-1";
    private String index = "test_index";
    private String type = "test_type";
    private String source_user = "englishtown";
    private String source_message = "vertx elastic search";

    @Test
    public void testIndex() throws Exception {

        JsonObject message = new JsonObject()
                .putString("action", "index")
                .putString("index", index)
                .putString("type", type)
                .putString("id", id)
                .putObject("source", new JsonObject()
                        .putString("user", source_user)
                        .putString("message", source_message)
                );

        vertx.eventBus().send(ElasticSearch.DEFAULT_ADDRESS, message, new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> reply) {
                JsonObject body = reply.body();
                assertEquals("ok", body.getString("status"));
                assertEquals(index, body.getString("index"));
                assertEquals(type, body.getString("type"));
                assertEquals(id, body.getString("id"));
                assertTrue(body.getInteger("version", 0) > 0);
                testComplete();
            }
        });

    }

    @Test
    public void testGet() throws Exception {

        JsonObject message = new JsonObject()
                .putString("action", "get")
                .putString("index", index)
                .putString("type", type)
                .putString("id", id);

        vertx.eventBus().send(ElasticSearch.DEFAULT_ADDRESS, message, new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> reply) {
                JsonObject body = reply.body();
                assertEquals("ok", body.getString("status"));
                assertEquals(index, body.getString("index"));
                assertEquals(type, body.getString("type"));
                assertEquals(id, body.getString("id"));
                assertTrue(body.getInteger("version", 0) > 0);
                JsonObject source = body.getObject("source");
                assertNotNull(source);
                if (source != null) {
                    assertEquals(source_user, source.getString("user"));
                    assertEquals(source_message, source.getString("message"));
                }
                testComplete();
            }
        });

    }

    /**
     * Override this method to signify that start is complete sometime _after_ the start() method has returned
     * This is useful if your verticle deploys other verticles or modules and you don't want this verticle to
     * be considered started until the other modules and verticles have been started.
     *
     * @param startedResult When you are happy your verticle is started set the result
     */
    @Override
    public void start(final Future<Void> startedResult) {

        container.deployVerticle(ElasticSearch.class.getName(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> result) {
                if (result.succeeded()) {
                    start();
                    startedResult.setResult(null);
                } else {
                    startedResult.setFailure(result.cause());
                }
            }
        });

    }

}
