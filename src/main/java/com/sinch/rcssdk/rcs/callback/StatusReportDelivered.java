package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.StatusReportType;

public class StatusReportDelivered extends StatusReport {

    public StatusReportDelivered() {
        super(StatusReportType.delivered);
    }

}
