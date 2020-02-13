package com.sinch.rcssdk.rcs.message.component.action;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinch.rcssdk.rcs.message.enums.ActionType;

@JsonIgnoreProperties({"type"})
public class DialPhoneNumber extends Action {

    private String phone_number;

    public DialPhoneNumber() {
        super(ActionType.dial_phone_number);
    }

    public DialPhoneNumber(String phoneNumber) {
        super(ActionType.dial_phone_number);
        this.phone_number = phoneNumber;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
