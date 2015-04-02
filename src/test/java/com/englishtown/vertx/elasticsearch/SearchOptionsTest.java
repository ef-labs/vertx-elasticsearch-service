package com.englishtown.vertx.elasticsearch;

import io.vertx.core.json.JsonObject;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
                .setExtraSource(new JsonObject().put("extra", "1"));

        json1 = options1.toJson();

        SearchOptions options2 = new SearchOptions(options1);
        JsonObject json2 = options2.toJson();

        assertEquals(json1.encode(), json2.encode());

        options2 = new SearchOptions(json2);
        json2 = options2.toJson();

        assertEquals(json1.encode(), json2.encode());

    }

}