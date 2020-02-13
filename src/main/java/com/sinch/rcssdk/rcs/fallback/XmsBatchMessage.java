package com.sinch.rcssdk.rcs.fallback;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class XmsBatchMessage {
    private FallbackType type;
    private String from;
    private String text;
    private String udh;
    private String campaign_id;
    private DeliveryReportType delivery_report;
    private String expire_at;
    private String callback_url;

    public XmsBatchMessage() {

    }

    public XmsBatchMessage(FallbackType type, String from, String text) {
        this.type = type;
        this.from = from;
        this.text = text;
    }

    public FallbackType getType() {
        return type;
    }

    public void setType(FallbackType type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUdh() {
        return udh;
    }

    public void setUdh(String udh) {
        this.udh = udh;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public DeliveryReportType getDelivery_report() {
        return delivery_report;
    }

    public void setDelivery_report(DeliveryReportType delivery_report) {
        this.delivery_report = delivery_report;
    }

    public String getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(String expire_at) {
        this.expire_at = expire_at;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

}
