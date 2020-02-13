package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.CallbackMessageType;

public class TextMessage extends CallbackMessage {

    private String text;

    public TextMessage() {
        super(CallbackMessageType.text);
    }

    public TextMessage(String text) {
        super(CallbackMessageType.text);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
