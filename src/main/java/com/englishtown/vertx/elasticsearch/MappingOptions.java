package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Options for admin put mapping operations
 */
@DataObject
public class MappingOptions {

    private Boolean ignoreConflicts;

    public static final String JSON_FIELD_IGNORE_CONFLICTS = "ignoreConflicts";

    public MappingOptions() {
    }

    public MappingOptions(MappingOptions other) {
        ignoreConflicts = other.ignoreConflicts;
    }

    public MappingOptions(JsonObject json) {

        ignoreConflicts = json.getBoolean(JSON_FIELD_IGNORE_CONFLICTS);

    }

    public Boolean shouldIgnoreConflicts() {
        return ignoreConflicts;
    }

    public MappingOptions setIgnoreConflicts(Boolean ignoreConflicts) {
        this.ignoreConflicts = ignoreConflicts;
        return this;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        if (ignoreConflicts != null) json.put(JSON_FIELD_IGNORE_CONFLICTS, ignoreConflicts);

        return json;
    }

}
