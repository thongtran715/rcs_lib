package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.StatusReportType;

public class StatusReportCapabilityLookupDispatched extends StatusReport {

    public StatusReportCapabilityLookupDispatched() {
        super(StatusReportType.capability_lookup_dispatched);
    }

}
