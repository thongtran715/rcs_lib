package com.sinch.rcssdk.rcs.callback;


import com.sinch.rcssdk.rcs.callback.enums.CallbackMessageType;

public class LocationMessage extends CallbackMessage {

    private double latitude;
    private double longitude;

    public LocationMessage() {
        super(CallbackMessageType.location);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
