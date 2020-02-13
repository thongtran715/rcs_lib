package com.sinch.rcssdk.rcs.message.component.action;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sinch.rcssdk.rcs.message.enums.ActionType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateCalendarEvent.class, name = "create_calendar_event"),
        @JsonSubTypes.Type(value = DialPhoneNumber.class, name = "dial_phone_number"),
        @JsonSubTypes.Type(value = OpenUrl.class, name = "open_url"),
        @JsonSubTypes.Type(value = RequestLocationPush.class, name = "request_location_push"),
        @JsonSubTypes.Type(value = ShowLocation.class, name = "show_location")
})
public abstract class Action {
    private ActionType type;

    public Action() {
        this.type = null;
    }

    public Action(ActionType type) {
        this.type = type;
    }

    public void setActionType(ActionType type) {
        this.type = type;
    }

    public ActionType getType() {
        return this.type;
    }
}
