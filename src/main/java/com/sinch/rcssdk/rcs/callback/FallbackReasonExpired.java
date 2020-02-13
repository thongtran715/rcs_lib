package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.FallbackReasonType;

public class FallbackReasonExpired extends FallbackReason {

    public FallbackReasonExpired() {
        super(FallbackReasonType.expired);
    }

}
