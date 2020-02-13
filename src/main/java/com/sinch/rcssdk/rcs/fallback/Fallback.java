package com.sinch.rcssdk.rcs.fallback;

public class Fallback {
    private boolean enabled;

    public Fallback() {
        enabled = false;
    }

    public Fallback(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
