package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.StatusReportType;

public class StatusReportAborted extends StatusReport {

    private boolean revoked;
    private boolean expired;

    public StatusReportAborted() {
        super(StatusReportType.queued);
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

}
