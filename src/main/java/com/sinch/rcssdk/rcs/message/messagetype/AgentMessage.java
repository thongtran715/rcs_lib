package com.sinch.rcssdk.rcs.message.messagetype;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.sinch.rcssdk.rcs.fallback.FallbackInfo;
import com.sinch.rcssdk.rcs.message.Utils.UtilsToString;
import com.sinch.rcssdk.rcs.message.component.richcard.ExpireInfo;
import com.sinch.rcssdk.rcs.message.component.suggestions.Suggestion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AgentMessage {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message_id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String to;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Message message;

    private Supplier supplier;

    private List<Suggestion> suggestions;

    private ExpireInfo expire;

    private FallbackInfo fallback;

    public AgentMessage() {
        this.message_id = UUID.randomUUID().toString();
        this.suggestions = new ArrayList<Suggestion>();
    }


    public AgentMessage(String to, Message message, FallbackInfo fallback) {
        this();
        this.to = to;
        this.message = message;
        this.fallback = fallback;
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getMessage_id() {
        return this.message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<Suggestion> getSuggestions() {
        return this.suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public ExpireInfo getExpire() {
        return this.expire;
    }

    public void setExpire(ExpireInfo expire) {
        this.expire = expire;
    }

    public FallbackInfo getFallback() {
        return this.fallback;
    }

    public void setFallback(FallbackInfo info) {
        this.fallback = info;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return UtilsToString.convertString(this);
    }

    public enum Supplier {
        GOOGLE("GOOGLE"), MAAP_SAMSUNG("MAAP_SAMSUNG");
        private String supply;

        Supplier(String supply) {
            this.supply = supply;
        }

        @Override
        public String toString() {
            return String.valueOf(supply);
        }
    }

}
