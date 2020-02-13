package com.sinch.rcssdk.rcs.message.component.action;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinch.rcssdk.rcs.message.enums.ActionType;
@JsonIgnoreProperties({"type"})
public class ShowLocation extends Action {

    private float latitude;
    private float longitude;
    private String label;

    public ShowLocation() {
        super(ActionType.show_location);
    }

    public ShowLocation(float latitude, float longitude) {
        super(ActionType.show_location);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ShowLocation(float latitude, float longitude, String label) {
        super(ActionType.show_location);
        this.latitude = latitude;
        this.longitude = longitude;
        this.label = label;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
