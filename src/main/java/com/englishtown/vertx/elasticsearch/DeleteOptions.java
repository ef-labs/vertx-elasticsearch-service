package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Delete operation options
 */
@DataObject
public class DeleteOptions extends AbstractWriteOptions<DeleteOptions> {

    public DeleteOptions() {
    }

    public DeleteOptions(DeleteOptions other) {
        super(other);

    }

    public DeleteOptions(JsonObject json) {
        super(json);

    }

    @Override
    public JsonObject toJson() {
        return super.toJson();
    }
}
