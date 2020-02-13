package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.StatusReportType;

public class StatusReportDispatched extends StatusReport {

    public StatusReportDispatched() {
        super(StatusReportType.dispatched);
    }

}
