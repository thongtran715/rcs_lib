package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.StatusReportType;

public class StatusReportDisplayed extends StatusReport {

    public StatusReportDisplayed() {
        super(StatusReportType.displayed);
    }

}
