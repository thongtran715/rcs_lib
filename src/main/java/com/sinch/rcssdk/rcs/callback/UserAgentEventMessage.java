package com.sinch.rcssdk.rcs.callback;

import com.sinch.rcssdk.rcs.callback.enums.RcsCallbackType;

public class UserAgentEventMessage extends Callback {

    private String from;
    private UserAgentEvent event;

    public UserAgentEventMessage() {
        super(RcsCallbackType.user_agent_event_rcs);
    }

    public UserAgentEventMessage(UserAgentEvent event, String from) {
        super(RcsCallbackType.user_agent_event_rcs);
        this.event = event;
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public UserAgentEvent getEvent() {
        return event;
    }

    public void setEvent(UserAgentEvent event) {
        this.event = event;
    }

}
