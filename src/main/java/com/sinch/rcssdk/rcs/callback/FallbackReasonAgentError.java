package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.FallbackReasonType;

public class FallbackReasonAgentError extends FallbackReason {

    private int code;
    private String reason;

    public FallbackReasonAgentError() {
        super(FallbackReasonType.capability_unsupported);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
