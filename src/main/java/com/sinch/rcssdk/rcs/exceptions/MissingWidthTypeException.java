package com.sinch.rcssdk.rcs.exceptions;

public class MissingWidthTypeException extends Exception {
    @Override
    public String toString() {
        return "Missing Width Type Exception";
    }

    @Override
    public String getMessage() {
        return this.toString();
    }
}
