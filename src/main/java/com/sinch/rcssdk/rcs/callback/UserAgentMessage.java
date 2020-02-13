package com.sinch.rcssdk.rcs.callback;

import com.sinch.rcssdk.rcs.callback.enums.RcsCallbackType;

public class UserAgentMessage extends Callback {

    private String message_id;
    private String from;
    private CallbackMessage message;

    private UserAgentMessage() {
        super(RcsCallbackType.user_agent_message_rcs);
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public CallbackMessage getMessage() {
        return message;
    }

    public void setMessage(CallbackMessage message) {
        this.message = message;
    }

}
