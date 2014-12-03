package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.elasticsearch.action.search.SearchType;

import java.util.ArrayList;
import java.util.List;

/**
 * Search operation options
 */
@Options
public class SearchOptions {

    private List<String> types = new ArrayList<>();
    private JsonObject query;
    private JsonObject filter;
    private JsonObject facets;
    private SearchType searchType;
    private String scroll;
    private Integer size;
    private Integer from;
    private List<String> fields = new ArrayList<>();
    private Long timeout;

    public static final String JSON_FIELD_QUERY = "query";
    public static final String JSON_FIELD_FILTER = "filter";
    public static final String JSON_FIELD_FACETS = "facets";
    public static final String JSON_FIELD_SEARCH_TYPE = "searchType";
    public static final String JSON_FIELD_SCROLL = "scroll";
    public static final String JSON_FIELD_SIZE = "size";
    public static final String JSON_FIELD_FROM = "from";
    public static final String JSON_FIELD_TIMEOUT = "timeout";
    public static final String JSON_FIELD_TYPES = "types";
    public static final String JSON_FIELD_FIELDS = "fields";

    public SearchOptions() {
    }

    public SearchOptions(SearchOptions other) {
        types = other.getTypes();
        query = other.getQuery();
        filter = other.getFilter();
        facets = other.getFacets();
        searchType = other.getSearchType();
        scroll = other.getScroll();
        size = other.getSize();
        from = other.getFrom();
        fields = other.getFields();
        timeout = other.getTimeout();
    }

    public SearchOptions(JsonObject json) {

        query = json.getJsonObject(JSON_FIELD_QUERY);
        filter = json.getJsonObject(JSON_FIELD_FILTER);
        facets = json.getJsonObject(JSON_FIELD_FACETS);
        scroll = json.getString(JSON_FIELD_SCROLL);
        size = json.getInteger(JSON_FIELD_SIZE);
        from = json.getInteger(JSON_FIELD_FROM);
        timeout = json.getLong(JSON_FIELD_TIMEOUT);

        String str = json.getString(JSON_FIELD_SEARCH_TYPE);
        if (str != null) {
            searchType = SearchType.fromString(str);
        }

        JsonArray typesJson = json.getJsonArray(JSON_FIELD_TYPES);
        if (typesJson != null) {
            for (int i = 0; i < typesJson.size(); i++) {
                types.add(typesJson.getString(i));
            }
        }

        JsonArray fieldsJson = json.getJsonArray(JSON_FIELD_FIELDS);
        if (fieldsJson != null) {
            for (int i = 0; i < fieldsJson.size(); i++) {
                fields.add(fieldsJson.getString(i));
            }
        }

    }

    public List<String> getTypes() {
        return types;
    }

    public SearchOptions addType(String type) {
        types.add(type);
        return this;
    }

    public JsonObject getQuery() {
        return query;
    }

    public SearchOptions setQuery(JsonObject query) {
        this.query = query;
        return this;
    }

    public JsonObject getFilter() {
        return filter;
    }

    public SearchOptions setFilter(JsonObject filter) {
        this.filter = filter;
        return this;
    }

    public JsonObject getFacets() {
        return facets;
    }

    public SearchOptions setFacets(JsonObject facets) {
        this.facets = facets;
        return this;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public SearchOptions setSearchType(SearchType searchType) {
        this.searchType = searchType;
        return this;
    }

    public String getScroll() {
        return scroll;
    }

    public SearchOptions setScroll(String scroll) {
        this.scroll = scroll;
        return this;
    }

    public Integer getSize() {
        return size;
    }

    public SearchOptions setSize(Integer size) {
        this.size = size;
        return this;
    }

    public Integer getFrom() {
        return from;
    }

    public SearchOptions setFrom(Integer from) {
        this.from = from;
        return this;
    }

    public Long getTimeout() {
        return timeout;
    }

    public SearchOptions setTimeout(Long timeout) {
        this.timeout = timeout;
        return this;
    }

    public List<String> getFields() {
        return fields;
    }

    public SearchOptions addField(String field) {
        fields.add(field);
        return this;
    }

    public JsonObject toJson() {

        JsonObject json = new JsonObject();

        if (query != null) {
            json.put(JSON_FIELD_QUERY, query);
        }
        if (filter != null) {
            json.put(JSON_FIELD_FILTER, filter);
        }
        if (facets != null) {
            json.put(JSON_FIELD_FACETS, facets);
        }
        if (scroll != null) {
            json.put(JSON_FIELD_SCROLL, scroll);
        }
        if (size != null) {
            json.put(JSON_FIELD_SIZE, size);
        }
        if (from != null) {
            json.put(JSON_FIELD_FROM, from);
        }
        if (timeout != null) {
            json.put(JSON_FIELD_TIMEOUT, timeout);
        }
        if (searchType != null) {
            json.put(JSON_FIELD_SEARCH_TYPE, searchType.name().toLowerCase());
        }
        if (!types.isEmpty()) {
            json.put(JSON_FIELD_TYPES, new JsonArray(types));
        }
        if (!fields.isEmpty()) {
            json.put(JSON_FIELD_FIELDS, new JsonArray(fields));
        }

        return json;
    }
}
