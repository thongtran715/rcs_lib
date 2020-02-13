package com.sinch.rcssdk.rcs.message.messagetype;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sinch.rcssdk.rcs.message.enums.MessageType;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"type"})
public class TextMessage extends Message {

    private String text;

    public TextMessage() {
        super(MessageType.text);
    }

    public TextMessage(String text) {
        this();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void generateFallback() {
        // TODO Auto-generated method stub

    }

}

