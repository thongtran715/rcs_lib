package com.sinch.rcssdk.rcs.message.component.agentevent;

import com.sinch.rcssdk.rcs.message.Utils.UtilsToString;

public class AgentEventSup {
    private String to;
    private String event_id;
    private AgentEvent event;

    public AgentEventSup() {
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getEvent_id() {
        return this.event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public AgentEvent getEvent() {
        return this.event;
    }

    public void setEvent(AgentEvent event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return UtilsToString.convertString(this);
    }

}
