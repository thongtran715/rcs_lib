package com.sinch.rcssdk.rcs.message.component.agentevent;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinch.rcssdk.rcs.message.enums.AgentEventType;

@JsonIgnoreProperties({"type"})
public class AgentComposingEvent extends AgentEvent {

    public AgentComposingEvent() {
        super(AgentEventType.agent_composing);
    }

}
