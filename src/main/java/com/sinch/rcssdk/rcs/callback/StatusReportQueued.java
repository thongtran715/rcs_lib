package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.StatusReportType;

public class StatusReportQueued extends StatusReport {

    public StatusReportQueued() {
        super(StatusReportType.queued);
    }

}
