package com.sinch.rcssdk.rcs.callback;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sinch.rcssdk.rcs.callback.enums.StatusReportType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@Type(value = StatusReportQueued.class, name = "queued"),
        @Type(value = StatusReportCapabilityLookupDispatched.class, name = "capability_lookup_dispatched"),
        @Type(value = StatusReportDispatched.class, name = "dispatched"),
        @Type(value = StatusReportFallbackDispatched.class, name = "fallback_dispatched"),
        @Type(value = StatusReportAborted.class, name = "aborted"),
        @Type(value = StatusReportFailed.class, name = "failed"),
        @Type(value = StatusReportDelivered.class, name = "delivered"),
        @Type(value = StatusReportDisplayed.class, name = "displayed"),})
public abstract class StatusReport {

    private StatusReportType type;

    public StatusReport() {

    }

    public StatusReport(StatusReportType type) {
        this.type = type;
    }

    @JsonProperty("type")
    public StatusReportType getType() {
        return type;
    }

    public void setType(StatusReportType type) {
        this.type = type;
    }

}
