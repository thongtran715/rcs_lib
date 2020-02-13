package com.sinch.rcssdk.rcs.message.component.action;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinch.rcssdk.rcs.message.enums.ActionType;
@JsonIgnoreProperties({"type"})
public class RequestLocationPush extends Action {

    public RequestLocationPush() {
        super(ActionType.request_location_push);
    }

}
