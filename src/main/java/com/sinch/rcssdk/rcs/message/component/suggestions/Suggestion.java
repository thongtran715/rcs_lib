package com.sinch.rcssdk.rcs.message.component.suggestions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sinch.rcssdk.rcs.message.component.postback.PostBack;
import com.sinch.rcssdk.rcs.message.enums.SuggestionType;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SuggestedAction.class, name = "action"),
        @JsonSubTypes.Type(value = SuggestedReply.class, name = "reply"),

})
public abstract class Suggestion {
    private SuggestionType type;
    private String display_text;
    private PostBack postback;

    public Suggestion(SuggestionType type) {
        this.type = type;
    }

    public Suggestion(SuggestionType type, String display_text, PostBack postback) {
        this.type = type;
        this.display_text = display_text;
        this.postback = postback;
    }

    public SuggestionType getType() {
        return type;
    }

    public void setType(SuggestionType type) {
        this.type = type;
    }

    public String getDisplay_text() {
        return display_text;
    }

    public void setDisplay_text(String display_text) {
        this.display_text = display_text;
    }

    public PostBack getPostback() {
        return postback;
    }

    public void setPostback(PostBack postback) {
        this.postback = postback;
    }

    public void updatePostback(String newMsgId) {
        this.postback.setData(newMsgId + "_" + postback.getData().split("_")[1]);
    }

}
