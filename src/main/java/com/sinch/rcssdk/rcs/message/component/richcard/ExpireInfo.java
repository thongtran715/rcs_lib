package com.sinch.rcssdk.rcs.message.component.richcard;

public class ExpireInfo {
    private int timeout;
    private boolean revoke;

    public ExpireInfo() {

    }

    public ExpireInfo(int timeout, boolean revoke) {
        this.timeout = timeout;
        this.revoke = revoke;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isRevoke() {
        return revoke;
    }

    public void setRevoke(boolean revoke) {
        this.revoke = revoke;
    }

}
