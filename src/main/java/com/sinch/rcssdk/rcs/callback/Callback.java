package com.sinch.rcssdk.rcs.callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinch.rcssdk.rcs.callback.enums.RcsCallbackType;

import java.io.IOException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = StatusReportMessage.class, name = "status_report_rcs"),
        @JsonSubTypes.Type(value = UserAgentEventMessage.class, name = "user_agent_event_rcs"),
        @JsonSubTypes.Type(value = UserAgentMessage.class, name = "user_agent_message_rcs"),})
public abstract class Callback {

    private RcsCallbackType type;

    public Callback(RcsCallbackType type) {
        this.type = type;
    }

    @JsonProperty("type")
    public RcsCallbackType getType() {
        return type;
    }

    public void setType(RcsCallbackType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException c) {
            c.printStackTrace();
        }
        return null;
    }

}
