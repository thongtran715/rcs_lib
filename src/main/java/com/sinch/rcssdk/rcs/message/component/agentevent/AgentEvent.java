package com.sinch.rcssdk.rcs.message.component.agentevent;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sinch.rcssdk.rcs.message.enums.AgentEventType;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AgentComposingEvent.class, name = "agent_composing"),
        @JsonSubTypes.Type(value = AgentReadEvent.class, name = "agent_read"),
})
public abstract class AgentEvent {

    private AgentEventType type;


    public AgentEvent() {
    }

    public AgentEvent(AgentEventType type) {
        this.type = type;
    }

    public AgentEventType getType() {
        return type;
    }

    public void setType(AgentEventType type) {
        this.type = type;
    }


}
