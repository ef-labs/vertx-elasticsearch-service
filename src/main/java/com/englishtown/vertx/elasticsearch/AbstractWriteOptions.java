package com.englishtown.vertx.elasticsearch;

import io.vertx.core.json.JsonObject;
import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.support.replication.ReplicationType;

/**
 * Abstract options
 */
public abstract class AbstractWriteOptions<T extends AbstractWriteOptions<T>> extends AbstractOptions<T> {

    private ReplicationType replicationType;
    private WriteConsistencyLevel consistencyLevel;
    private String timeout;

    public static final String FIELD_REPLICATION_TYPE = "replicationType";
    public static final String FIELD_CONSISTENCY_LEVEL = "consistencyLevel";
    public static final String FIELD_TIMEOUT = "timeout";

    protected AbstractWriteOptions() {
    }

    protected AbstractWriteOptions(T other) {
        super(other);
        replicationType = other.getReplicationType();
        consistencyLevel = other.getConsistencyLevel();
        timeout = other.getTimeout();
    }

    protected AbstractWriteOptions(JsonObject json) {
        super(json);

        timeout = json.getString(FIELD_TIMEOUT);

        String s = json.getString(FIELD_REPLICATION_TYPE);
        if (s != null) replicationType = ReplicationType.fromString(s);
        s = json.getString(FIELD_CONSISTENCY_LEVEL);
        if (s != null) consistencyLevel = WriteConsistencyLevel.fromString(s);

    }

    public ReplicationType getReplicationType() {
        return replicationType;
    }

    public T setReplicationType(ReplicationType replicationType) {
        this.replicationType = replicationType;
        return returnThis();
    }

    public WriteConsistencyLevel getConsistencyLevel() {
        return consistencyLevel;
    }

    public T setConsistencyLevel(WriteConsistencyLevel consistencyLevel) {
        this.consistencyLevel = consistencyLevel;
        return returnThis();
    }

    public String getTimeout() {
        return timeout;
    }

    public T setTimeout(String timeout) {
        this.timeout = timeout;
        return returnThis();
    }

    public JsonObject toJson() {
        JsonObject json = super.toJson();

        if (getReplicationType() != null) {
            json.put(FIELD_REPLICATION_TYPE, getReplicationType().toString().toLowerCase());
        }
        if (getConsistencyLevel() != null) {
            json.put(FIELD_CONSISTENCY_LEVEL, getConsistencyLevel().toString().toLowerCase());
        }
        if (getTimeout() != null) json.put(FIELD_TIMEOUT, getTimeout());

        return json;
    }

}
