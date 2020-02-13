package com.sinch.rcssdk.rcs.callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sinch.rcssdk.rcs.callback.enums.CallbackMessageType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = TextMessage.class, name = "text"), @JsonSubTypes.Type(value = FileMessage.class, name = "file"),
        @JsonSubTypes.Type(value = SuggestionResponse.class, name = "suggestion_response"),
        @JsonSubTypes.Type(value = LocationMessage.class, name = "location")})
public abstract class CallbackMessage {
    private CallbackMessageType type;

    public CallbackMessage(CallbackMessageType type) {
        this.type = type;
    }

    @JsonProperty("type")
    public CallbackMessageType getType() {
        return type;
    }

    public void setType(CallbackMessageType type) {
        this.type = type;
    }

}
