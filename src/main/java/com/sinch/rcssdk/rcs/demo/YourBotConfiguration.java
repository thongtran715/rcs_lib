package com.sinch.rcssdk.rcs.demo;

import com.sinch.rcssdk.rcs.chatflow.AgentConfiguration;
import com.sinch.rcssdk.rcs.chatflow.RCSConfigureType;

public class YourBotConfiguration extends AgentConfiguration {

    public YourBotConfiguration(RCSConfigureType type) {
        super(type);
        this.TOKEN = "w2L4hTjRU99ksykf7vMRUHmY";
        this.AGENT_ID = "1";
    }
}
