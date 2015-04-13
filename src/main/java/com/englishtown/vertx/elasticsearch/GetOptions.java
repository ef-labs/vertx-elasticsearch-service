package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Get operation options
 */
@DataObject
public class GetOptions extends AbstractOptions<GetOptions> {

    private String preference;
    private List<String> fields = new ArrayList<>();
    private Boolean fetchSource;
    private List<String> fetchSourceIncludes = new ArrayList<>();
    private List<String> fetchSourceExcludes = new ArrayList<>();
    private Boolean transformSource;
    private Boolean realtime;
    private Boolean ignoreErrorsOnGeneratedFields;

    public static final String FIELD_PREFERENCE = "preference";
    public static final String FIELD_FIELDS = "fields";
    public static final String FIELD_FETCH_SOURCE = "fetchSource";
    public static final String FIELD_FETCH_SOURCE_INCLUDES = "fetchSourceIncludes";
    public static final String FIELD_FETCH_SOURCE_EXCLUDES = "fetchSourceExcludes";
    public static final String FIELD_TRANSFORM_SOURCE = "transformSource";
    public static final String FIELD_REALTIME = "realtime";
    public static final String FIELD_IGNORE_ERROR_ON_GENERATED_FIELDS = "ignoreErrorsOnGeneratedFields";

    public GetOptions() {
    }

    public GetOptions(GetOptions other) {
        super(other);

        preference = other.getPreference();
        fields.addAll(other.getFields());
        fetchSource = other.isFetchSource();
        fetchSourceIncludes = other.getFetchSourceIncludes();
        fetchSourceExcludes = other.getFetchSourceExcludes();
        transformSource = other.isTransformSource();
        realtime = other.isRealtime();
        ignoreErrorsOnGeneratedFields = other.isIgnoreErrorsOnGeneratedFields();
    }

    public GetOptions(JsonObject json) {
        super(json);

        preference= json.getString(FIELD_PREFERENCE);
        fields = json.getJsonArray(FIELD_FIELDS, new JsonArray()).getList();
        fetchSource = json.getBoolean(FIELD_FETCH_SOURCE);
        fetchSourceIncludes = json.getJsonArray(FIELD_FETCH_SOURCE_INCLUDES, new JsonArray()).getList();
        fetchSourceExcludes = json.getJsonArray(FIELD_FETCH_SOURCE_EXCLUDES, new JsonArray()).getList();
        transformSource = json.getBoolean(FIELD_TRANSFORM_SOURCE);
        realtime = json.getBoolean(FIELD_REALTIME);
        ignoreErrorsOnGeneratedFields = json.getBoolean(FIELD_IGNORE_ERROR_ON_GENERATED_FIELDS);

    }

    public String getPreference() {
        return preference;
    }

    public GetOptions setPreference(String preference) {
        this.preference = preference;
        return this;
    }

    public List<String> getFields() {
        return fields;
    }

    public GetOptions addField(String field) {
        this.fields.add(field);
        return this;
    }

    public Boolean isFetchSource() {
        return fetchSource;
    }

    public GetOptions setFetchSource(Boolean fetchSource) {
        this.fetchSource = fetchSource;
        return this;
    }

    public List<String> getFetchSourceIncludes() {
        return fetchSourceIncludes;
    }

    public List<String> getFetchSourceExcludes() {
        return fetchSourceExcludes;
    }

    public GetOptions setFetchSource(List<String> includes, List<String> excludes) {
        if (includes == null || includes.isEmpty()) {
            fetchSourceIncludes.clear();
        } else {
            fetchSourceIncludes.addAll(includes);
        }
        if (excludes == null || excludes.isEmpty()) {
            fetchSourceExcludes.clear();
        } else {
            fetchSourceExcludes.addAll(excludes);
        }
        return this;
    }

    public Boolean isTransformSource() {
        return transformSource;
    }

    public GetOptions setTransformSource(Boolean transformSource) {
        this.transformSource = transformSource;
        return this;
    }

    public Boolean isRealtime() {
        return realtime;
    }

    public GetOptions setRealtime(Boolean realtime) {
        this.realtime = realtime;
        return this;
    }

    public Boolean isIgnoreErrorsOnGeneratedFields() {
        return ignoreErrorsOnGeneratedFields;
    }

    public GetOptions setIgnoreErrorsOnGeneratedFields(Boolean ignoreErrorsOnGeneratedFields) {
        this.ignoreErrorsOnGeneratedFields = ignoreErrorsOnGeneratedFields;
        return this;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = super.toJson();

        if (getPreference() != null) json.put(FIELD_PREFERENCE, getPreference());
        if (!getFields().isEmpty()) json.put(FIELD_FIELDS, new JsonArray(getFields()));
        if (isFetchSource() != null) json.put(FIELD_FETCH_SOURCE, isFetchSource());
        if (!getFetchSourceIncludes().isEmpty()) json.put(FIELD_FETCH_SOURCE_INCLUDES, new JsonArray(getFetchSourceIncludes()));
        if (!getFetchSourceExcludes().isEmpty()) json.put(FIELD_FETCH_SOURCE_EXCLUDES, new JsonArray(getFetchSourceExcludes()));
        if (isTransformSource() != null) json.put(FIELD_TRANSFORM_SOURCE, isTransformSource());
        if (isRealtime() != null) json.put(FIELD_REALTIME, isRealtime());
        if (isIgnoreErrorsOnGeneratedFields() != null) json.put(FIELD_IGNORE_ERROR_ON_GENERATED_FIELDS, isIgnoreErrorsOnGeneratedFields());

        return json;
    }
}
