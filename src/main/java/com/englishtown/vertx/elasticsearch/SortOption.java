package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.elasticsearch.search.sort.SortOrder;

/**
 * Sort option
 */
@DataObject
public class SortOption {

    private String field;
    private SortOrder order;

    public static final String JSON_FIELD_FIELD = "field";
    public static final String JSON_FIELD_ORDER = "order";

    private static final String ASC = "asc";
    private static final String DESC = "desc";

    public SortOption() {
    }

    public SortOption(SortOption other) {
        field = other.getField();
        order = other.getOrder();
    }

    public SortOption(JsonObject json) {

        field = json.getString(JSON_FIELD_FIELD);
        String o = json.getString(JSON_FIELD_ORDER, ASC);

        switch (o) {
            case ASC:
                order = SortOrder.ASC;
                break;
            case DESC:
                order = SortOrder.DESC;
                break;
            default:
                throw new IllegalArgumentException("Order " + o + " is not supported");
        }

    }

    public String getField() {
        return field;
    }

    public SortOption setField(String field) {
        this.field = field;
        return this;
    }

    public SortOrder getOrder() {
        return order;
    }

    public SortOption setOrder(SortOrder order) {
        this.order = order;
        return this;
    }

    public JsonObject toJson() {
        return new JsonObject()
                .put(JSON_FIELD_FIELD, field)
                .put(JSON_FIELD_ORDER, order.toString());
    }

}
