package com.sinch.rcssdk.rcs.fallback;

public class FallbackConditions {
    private Fallback rcs_unavailable;
    private Fallback capability_unsupported;
    private Fallback expired;
    private Fallback agent_error;

    public FallbackConditions() {
        rcs_unavailable = new Fallback();
        capability_unsupported = new Fallback();
        expired = new Fallback();
        agent_error = new Fallback();
    }

    public FallbackConditions(Fallback rcs_unavailable, Fallback capability_unsupported, Fallback expired,
                              Fallback agent_error) {
        this.rcs_unavailable = rcs_unavailable;
        this.capability_unsupported = capability_unsupported;
        this.expired = expired;
        this.agent_error = agent_error;
    }

    public Fallback getRcs_unavailable() {
        return rcs_unavailable;
    }

    public void setRcs_unavailable(Fallback rcs_unavailable) {
        this.rcs_unavailable = rcs_unavailable;
    }

    public Fallback getCapability_unsupported() {
        return capability_unsupported;
    }

    public void setCapability_unsupported(Fallback capability_unsupported) {
        this.capability_unsupported = capability_unsupported;
    }

    public Fallback getExpired() {
        return expired;
    }

    public void setExpired(Fallback expired) {
        this.expired = expired;
    }

    public Fallback getAgent_error() {
        return agent_error;
    }

    public void setAgent_error(Fallback agent_error) {
        this.agent_error = agent_error;
    }

}
