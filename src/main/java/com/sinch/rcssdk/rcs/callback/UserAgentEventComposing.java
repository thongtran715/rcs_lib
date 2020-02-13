package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.UserAgentEventType;

public class UserAgentEventComposing extends UserAgentEvent {

    public UserAgentEventComposing() {
        super(UserAgentEventType.composing);
    }

}
