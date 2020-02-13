package com.sinch.rcssdk.rcs.fallback;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FallbackInfo {
    private XmsBatchMessage message;
    private FallbackConditions conditions;

    public FallbackInfo() {

    }

    public FallbackInfo(XmsBatchMessage message, FallbackConditions conditions) {
        this.message = message;
        this.conditions = conditions;
    }

    public XmsBatchMessage getMessage() {
        return message;
    }

    public void setMessage(XmsBatchMessage message) {
        this.message = message;
    }

    public FallbackConditions getConditions() {
        return conditions;
    }

    public void setConditions(FallbackConditions conditions) {
        this.conditions = conditions;
    }

}
