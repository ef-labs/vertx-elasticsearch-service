package com.englishtown.vertx.elasticsearch.integration;

import com.englishtown.promises.Promise;
import com.englishtown.promises.Runnable;
import com.englishtown.promises.Value;
import com.englishtown.promises.When;
import com.englishtown.vertx.elasticsearch.ElasticSearch;
import com.englishtown.vertx.promises.WhenEventBus;
import com.englishtown.vertx.promises.impl.DefaultWhenEventBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;

import java.util.ArrayList;
import java.util.List;

import static org.vertx.testtools.VertxAssert.*;

/**
 * {@link ElasticSearch} integration test
 */
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
                .putString(ElasticSearch.CONST_INDEX, index)
                .putString(ElasticSearch.CONST_TYPE, type)
                .putString(ElasticSearch.CONST_ID, id)
                .putObject(ElasticSearch.CONST_SOURCE, new JsonObject()
                        .putString("user", source_user)
                        .putString("message", source_message)
                );

        vertx.eventBus().send(ElasticSearch.DEFAULT_ADDRESS, message, new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> reply) {
                JsonObject body = reply.body();
                assertEquals("ok", body.getString("status"));
                assertEquals(index, body.getString(ElasticSearch.CONST_INDEX));
                assertEquals(type, body.getString(ElasticSearch.CONST_TYPE));
                assertEquals(id, body.getString(ElasticSearch.CONST_ID));
                assertTrue(body.getInteger(ElasticSearch.CONST_VERSION, 0) > 0);
                testComplete();
            }
        });

    }


    @Test
    public void testIndex_Multiple() throws Exception {

        int count = 100;
        WhenEventBus eventBus = new DefaultWhenEventBus(vertx, container);
        When<Message<JsonObject>, Void> when = new When<>();
        List<Promise<Message<JsonObject>, Void>> promises = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            JsonObject message = new JsonObject()
                    .putString("action", "index")
                    .putString(ElasticSearch.CONST_INDEX, index)
                    .putString(ElasticSearch.CONST_TYPE, type)
                    .putString(ElasticSearch.CONST_ID, id + i)
                    .putObject(ElasticSearch.CONST_SOURCE, new JsonObject()
                            .putString("user", source_user)
                            .putString("message", source_message + " " + i)
                    );
            promises.add(eventBus.<JsonObject>send(ElasticSearch.DEFAULT_ADDRESS, message));
        }

        when.all(promises,
                new Runnable<Promise<List<Message<JsonObject>>, Void>, List<Message<JsonObject>>>() {
                    @Override
                    public Promise<List<Message<JsonObject>>, Void> run(List<Message<JsonObject>> replies) {
                        assertEquals(100, replies.size());
                        for (Message<JsonObject> reply : replies) {
                            assertEquals("ok", reply.body().getString("status"));
                        }
                        testComplete();
                        return null;
                    }
                },
                new Runnable<Promise<List<Message<JsonObject>>, Void>, Value<List<Message<JsonObject>>>>() {
                    @Override
                    public Promise<List<Message<JsonObject>>, Void> run(Value<List<Message<JsonObject>>> value) {
                        fail();
                        testComplete();
                        return null;
                    }
                }
        );

    }

    @Test
    public void testGet() throws Exception {

        JsonObject message = new JsonObject()
                .putString("action", "get")
                .putString(ElasticSearch.CONST_INDEX, index)
                .putString(ElasticSearch.CONST_TYPE, type)
                .putString(ElasticSearch.CONST_ID, id);

        vertx.eventBus().send(ElasticSearch.DEFAULT_ADDRESS, message, new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> reply) {
                JsonObject body = reply.body();
                assertEquals("ok", body.getString("status"));
                assertEquals(index, body.getString(ElasticSearch.CONST_INDEX));
                assertEquals(type, body.getString(ElasticSearch.CONST_TYPE));
                assertEquals(id, body.getString(ElasticSearch.CONST_ID));
                assertTrue(body.getInteger(ElasticSearch.CONST_VERSION, 0) > 0);
                JsonObject source = body.getObject(ElasticSearch.CONST_SOURCE);
                assertNotNull(source);
                if (source != null) {
                    assertEquals(source_user, source.getString("user"));
                    assertEquals(source_message, source.getString("message"));
                }
                testComplete();
            }
        });

    }

    @Test
    public void testSearch_Simple() throws Exception {

        JsonObject message = new JsonObject()
                .putString("action", "search")
                .putString(ElasticSearch.CONST_INDEX, index)
                .putObject("query", new JsonObject().putObject("match_all", new JsonObject()));

        vertx.eventBus().send(ElasticSearch.DEFAULT_ADDRESS, message, new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> reply) {
                assertEquals("ok", reply.body().getString("status"));
                testComplete();
            }
        });

    }

    @Test
    public void testScroll() throws Exception {

        JsonObject message = new JsonObject()
                .putString("action", "search")
                .putString(ElasticSearch.CONST_INDEX, index)
                .putString("search_type", "scan")
                .putString("scroll", "5m")
                .putObject("query", new JsonObject().putObject("match_all", new JsonObject()));

        vertx.eventBus().send(ElasticSearch.DEFAULT_ADDRESS, message, new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> reply) {
                assertEquals("ok", reply.body().getString("status"));
                String scrollId = reply.body().getString("_scroll_id");
                assertNotNull(scrollId);

                JsonObject message = new JsonObject()
                        .putString("action", "scroll")
                        .putString("_scroll_id", scrollId)
                        .putString("scroll", "5m");

                vertx.eventBus().send(ElasticSearch.DEFAULT_ADDRESS, message, new Handler<Message<JsonObject>>() {
                    @Override
                    public void handle(Message<JsonObject> reply) {
                        assertEquals("ok", reply.body().getString("status"));
                        JsonObject hits = reply.body().getObject("hits");
                        assertNotNull(hits);
                        JsonArray hitsArray = hits.getArray("hits");
                        assertNotNull(hitsArray);
                        assertTrue(hitsArray.size() > 0);
                        testComplete();
                    }
                });
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
