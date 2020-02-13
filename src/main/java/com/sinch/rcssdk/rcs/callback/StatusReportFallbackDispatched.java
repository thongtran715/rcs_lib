package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.StatusReportType;

public class StatusReportFallbackDispatched extends StatusReport {

    private String external_ref;
    private boolean revoked;
    private FallbackReason reason;

    public StatusReportFallbackDispatched() {
        super(StatusReportType.fallback_dispatched);
    }

    public String getExternal_ref() {
        return external_ref;
    }

    public void setExternal_ref(String external_ref) {
        this.external_ref = external_ref;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public FallbackReason getReason() {
        return reason;
    }

    public void setReason(FallbackReason reason) {
        this.reason = reason;
    }

}
