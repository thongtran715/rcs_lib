package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.CallbackMessageType;

public class SuggestionResponse extends CallbackMessage {

    private String postback_data;
    private String text;

    public SuggestionResponse() {
        super(CallbackMessageType.suggestion_response);
    }

    public String getPostback_data() {
        return postback_data;
    }

    public void setPostback_data(String postback_data) {
        this.postback_data = postback_data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
