package com.englishtown.vertx.elasticsearch;

import io.vertx.core.json.JsonObject;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.script.ScriptService;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Unit tests for {@link SearchOptions}
 */
public class SearchOptionsTest {

    @Test
    public void testSearchOptions() throws Exception {

        SearchOptions options1 = new SearchOptions().addType("type1");
        JsonObject json1 = options1.toJson();

        assertEquals("{\"types\":[\"type1\"]}", json1.encode());

        options1 = new SearchOptions()
                .addType("type1")
                .addType("type2")
                .setSearchType(SearchType.QUERY_AND_FETCH)
                .setScroll("scroll")
                .setTimeout("timeout")
                .setTerminateAfter(10)
                .setRouting("routing")
                .setPreference("preference")
                .setQuery(new JsonObject().put("status", "ok"))
                .setPostFilter(new JsonObject().put("status2", "ok"))
                .setMinScore(10F)
                .setSize(50)
                .setFrom(1)
                .setExplain(true)
                .setVersion(true)
                .setFetchSource(true)
                .addField("field1")
                .addField("field2")
                .setTrackScores(true)
                .setAggregations(new JsonObject().put("name", "name"))
                .addSort("status", SortOrder.ASC)
                .addSort("insert_date", SortOrder.ASC)
                .setExtraSource(new JsonObject().put("extra", "1"))
                .setTemplateName("templateName")
                .setTemplateType(ScriptService.ScriptType.INDEXED)
                .setTemplateParams(new JsonObject().put("template_param", "sample_param"));

        json1 = options1.toJson();

        SearchOptions options2 = new SearchOptions(options1);
        JsonObject json2 = options2.toJson();

        assertEquals(json1.encode(), json2.encode());

        options2 = new SearchOptions(json2);
        json2 = options2.toJson();

        assertEquals(json1.encode(), json2.encode());

    }

    @Test
    public void testSetTemplateTypeFromJson() {

        JsonObject json = new JsonObject();

        try {
            json.put("templateType", "not-a-real-enum");
            new SearchOptions(json);
            fail("Expected exception");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        json.put("templateType", "file");
        SearchOptions options = new SearchOptions(json);
        assertEquals(options.getTemplateType(), ScriptService.ScriptType.FILE);

        json.put("templateType", "indexed");
        options = new SearchOptions(json);
        assertEquals(options.getTemplateType(), ScriptService.ScriptType.INDEXED);

        json.put("templateType", "inline");
        options = new SearchOptions(json);
        assertEquals(options.getTemplateType(), ScriptService.ScriptType.INLINE);

        json.remove("templateType");
        options = new SearchOptions(json);
        assertNull(options.getTemplateType());
    }

}
