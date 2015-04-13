package com.englishtown.vertx.elasticsearch;

import io.vertx.core.json.JsonObject;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link GetOptions}
 */
public class GetOptionsTest {

    @Test
    public void testToJson() throws Exception {

        GetOptions options1 = new GetOptions()
                .setPreference("preference");
        JsonObject json1 = options1.toJson();

        assertEquals(1, json1.fieldNames().size());
        assertEquals("{\"preference\":\"preference\"}", json1.encode());

        options1 = new GetOptions()
                .setPreference("preference")
                .addField("field1")
                .addField("field2")
                .setFetchSource(true)
                .setFetchSource(Arrays.asList("incl1", "incl2"), Arrays.asList("excl1", "excl2"))
                .setTransformSource(true)
                .setRealtime(true)
                .setIgnoreErrorsOnGeneratedFields(true);

        json1= options1.toJson();

        assertEquals(8, json1.fieldNames().size());

        GetOptions options2 = new GetOptions(json1);
        JsonObject json2 = options2.toJson();

        assertEquals(json1.encode(), json2.encode());

        options2 = new GetOptions(options1);
        json2 = options2.toJson();

        assertEquals(json1.encode(), json2.encode());

    }
}