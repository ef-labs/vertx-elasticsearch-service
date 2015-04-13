package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Search scroll options
 */
@DataObject
public class SearchScrollOptions {

    private String scroll;

    public static final String FIELD_SCROLL = "scroll";

    public SearchScrollOptions() {
    }

    public SearchScrollOptions(SearchScrollOptions other) {
        scroll = other.getScroll();
    }

    public SearchScrollOptions(JsonObject json) {
        scroll = json.getString(FIELD_SCROLL);
    }

    public String getScroll() {
        return scroll;
    }

    public SearchScrollOptions setScroll(String keepAlive) {
        this.scroll = keepAlive;
        return this;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        if (getScroll() != null) json.put(FIELD_SCROLL, getScroll());

        return json;
    }

}
