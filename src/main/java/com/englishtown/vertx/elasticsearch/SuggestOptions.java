package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author marcus
 * @since 1.0.0
 */
@DataObject
public class SuggestOptions extends AbstractOptions<SuggestOptions> {

    public static final String FIELD_SUGGESTION_NAME = "suggestionName";
    public static final String FIELD_SUGGESTION_TEXT = "suggestionText";
    public static final String FIELD_SUGGESTION_FIELD = "suggestionField";

    private String name;
    private String text;
    private String field;

    public SuggestOptions() {
    }

    public SuggestOptions(SuggestOptions other) {
        super(other);

        name = other.getName();
        text = other.getText();
        field = other.getField();
    }

    public SuggestOptions(JsonObject json) {
        super(json);

        name = json.getString(FIELD_SUGGESTION_NAME);
        text = json.getString(FIELD_SUGGESTION_TEXT);
        field = json.getString(FIELD_SUGGESTION_FIELD);
    }

    public String getName() {
        return name;
    }

    public SuggestOptions setName(final String name) {
        this.name = name;
        return this;
    }

    public String getText() {
        return text;
    }

    public SuggestOptions setText(final String text) {
        this.text = text;
        return this;
    }

    public String getField() {
        return field;
    }

    public SuggestOptions setField(final String field) {
        this.field = field;
        return this;
    }

    @Override
    public JsonObject toJson() {
        final JsonObject json = super.toJson();

        if (getName() != null) json.put(FIELD_SUGGESTION_NAME,getName());
        if (getText() != null) json.put(FIELD_SUGGESTION_TEXT,getText());
        if (getField() != null) json.put(FIELD_SUGGESTION_FIELD,getField());

        return json;
    }
}
