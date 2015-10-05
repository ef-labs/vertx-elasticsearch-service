package com.englishtown.vertx.elasticsearch;

import io.vertx.core.json.JsonObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link MappingOptions}
 */
public class MappingOptionsTest {

    @Test
    public void testToJson() throws Exception {

        MappingOptions options = new MappingOptions();

        JsonObject json1 = options.toJson();
        assertEquals(0, json1.fieldNames().size());

        options.setIgnoreConflicts(true);
        json1 = options.toJson();
        assertEquals(true, json1.getBoolean(MappingOptions.JSON_FIELD_IGNORE_CONFLICTS));

        MappingOptions options2 = new MappingOptions(json1);
        JsonObject json2 = options2.toJson();

        assertEquals(json1.encode(), json2.encode());

        options2 = new MappingOptions(options);
        json2 = options2.toJson();

        assertEquals(json1.encode(), json2.encode());

    }

}