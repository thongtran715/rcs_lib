package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.FallbackReasonType;

public class FallbackReasonCapabilityUnsupported extends FallbackReason {

    public FallbackReasonCapabilityUnsupported() {
        super(FallbackReasonType.capability_unsupported);
    }

}
