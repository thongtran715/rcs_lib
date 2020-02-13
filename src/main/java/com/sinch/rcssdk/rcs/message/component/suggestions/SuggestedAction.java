package com.sinch.rcssdk.rcs.message.component.suggestions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinch.rcssdk.rcs.message.component.action.Action;
import com.sinch.rcssdk.rcs.message.component.postback.PostBack;
import com.sinch.rcssdk.rcs.message.enums.SuggestionType;
@JsonIgnoreProperties({"type"})
public class SuggestedAction extends Suggestion {

    private Action action;

    public SuggestedAction() {
        super(SuggestionType.action);
    }

    public SuggestedAction(String display_text, PostBack postback) {
        super(SuggestionType.action, display_text, postback);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

}
