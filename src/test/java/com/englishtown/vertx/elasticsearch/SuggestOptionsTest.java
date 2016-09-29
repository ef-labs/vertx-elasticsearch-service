package com.englishtown.vertx.elasticsearch;

import io.vertx.core.json.JsonObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author marcus
 * @since 1.0.0
 */
public class SuggestOptionsTest {

    @Test
    public void testSuggestOptions() throws Exception {
        final SuggestOptions options = new SuggestOptions().setField("field").setName("name").setText("text").setSize(10);

        final JsonObject json = options.toJson();

        assertEquals(4, json.fieldNames().size());

        final SuggestOptions other = new SuggestOptions(options);
        final SuggestOptions otherFromJson = new SuggestOptions(json);

        assertEquals(json.encode(),other.toJson().encode());
        assertEquals(json.encode(),otherFromJson.toJson().encode());

    }
}
