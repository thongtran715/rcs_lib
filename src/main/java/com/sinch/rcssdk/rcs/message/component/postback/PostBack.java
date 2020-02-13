package com.sinch.rcssdk.rcs.message.component.postback;

public class PostBack {
    private String data;

    public PostBack() {
        data = "";
    }

    public PostBack(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
