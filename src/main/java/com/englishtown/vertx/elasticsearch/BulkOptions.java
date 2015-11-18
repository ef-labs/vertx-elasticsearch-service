package com.englishtown.vertx.elasticsearch;

import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Bulk operation options
 */
public class BulkOptions extends AbstractWriteOptions<BulkOptions> {

    private List<IndexOptions> indexOptionsList = new ArrayList<>();
    private List<UpdateOptions> updateOptionsList = new ArrayList<>();
    private List<DeleteOptions> deleteOptionsList = new ArrayList<>();

    public BulkOptions() {
    }

    public BulkOptions(BulkOptions other) {
        super(other);

        indexOptionsList.addAll(other.indexOptionsList);
        updateOptionsList.addAll(other.updateOptionsList);
        deleteOptionsList.addAll(other.deleteOptionsList);
    }

    public BulkOptions(JsonObject json) {
        super(json);


    }

    public JsonObject toJson() {
        JsonObject json = super.toJson();

        return json;
    }

}
