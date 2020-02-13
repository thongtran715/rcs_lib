package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.RcsCallbackType;

public class StatusReportMessage extends Callback {
    private String message_id;
    private String at;
    private StatusReport status_report;

    public StatusReportMessage() {
        super(RcsCallbackType.status_report_rcs);
    }

    public StatusReportMessage(String message_id, String at, StatusReport status_report) {
        super(RcsCallbackType.status_report_rcs);
        this.message_id = message_id;
        this.at = at;
        this.status_report = status_report;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public StatusReport getStatus_report() {
        return status_report;
    }

    public void setStatus_report(StatusReport status_report) {
        this.status_report = status_report;
    }

}
