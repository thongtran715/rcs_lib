package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.FallbackReasonType;

public class FallbackReasonRcsUnavailable extends FallbackReason {

    public FallbackReasonRcsUnavailable() {
        super(FallbackReasonType.rcs_unavailable);
    }

}
