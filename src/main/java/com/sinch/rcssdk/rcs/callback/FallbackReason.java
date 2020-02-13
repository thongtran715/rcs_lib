package com.sinch.rcssdk.rcs.callback;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sinch.rcssdk.rcs.callback.enums.FallbackReasonType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = FallbackReasonRcsUnavailable.class, name = "rcs_unavailable"),
        @JsonSubTypes.Type(value = FallbackReasonCapabilityUnsupported.class, name = "capability_unsupported"),
        @JsonSubTypes.Type(value = FallbackReasonExpired.class, name = "expired"),
        @JsonSubTypes.Type(value = FallbackReasonAgentError.class, name = "agent_error")})
public abstract class FallbackReason {

    FallbackReasonType type;

    public FallbackReason(FallbackReasonType type) {
        this.type = type;
    }

}
