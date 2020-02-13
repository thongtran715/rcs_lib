package com.sinch.rcssdk.rcs.message.component.suggestions;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinch.rcssdk.rcs.message.component.postback.PostBack;
import com.sinch.rcssdk.rcs.message.enums.SuggestionType;
@JsonIgnoreProperties({"type"})
public class SuggestedReply extends Suggestion {

    public SuggestedReply() {
        super(SuggestionType.reply);
    }

    public SuggestedReply(String display_text, PostBack postback) {
        super(SuggestionType.reply, display_text, postback);
    }

}

