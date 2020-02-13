package com.sinch.rcssdk.rcs.message.component.richcard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sinch.rcssdk.rcs.message.component.suggestions.Suggestion;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RichCardContent {
    private String title;
    private String description;
    private RichCardMedia media;
    private List<Suggestion> suggestions;

    public RichCardContent() {
        this.suggestions = new ArrayList<Suggestion>();
    }

    public RichCardContent(String title, String description, RichCardMedia media) {
        this.title = title;
        this.description = description;
        this.media = media;
        this.suggestions = new ArrayList<Suggestion>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RichCardMedia getMedia() {
        return media;
    }

    public void setMedia(RichCardMedia media) {
        this.media = media;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public void addSuggestion(Suggestion suggestion) {
        suggestions.add(suggestion);
    }

}
