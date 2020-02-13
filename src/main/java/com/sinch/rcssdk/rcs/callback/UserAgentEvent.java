package com.sinch.rcssdk.rcs.callback;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sinch.rcssdk.rcs.callback.enums.UserAgentEventType;

public class UserAgentEvent {

    private UserAgentEventType type;

    public UserAgentEvent() {

    }

    public UserAgentEvent(UserAgentEventType type) {
        this.type = type;
    }

    @JsonProperty("type")
    public UserAgentEventType getType() {
        return type;
    }

    public void setType(UserAgentEventType type) {
        this.type = type;
    }

}
