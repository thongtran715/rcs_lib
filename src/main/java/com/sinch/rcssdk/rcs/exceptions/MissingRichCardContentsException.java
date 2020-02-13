package com.sinch.rcssdk.rcs.exceptions;

public class MissingRichCardContentsException extends Exception {

    @Override
    public String toString() {
        return "Missing Rich Card Contents. ";
    }

    @Override
    public String getMessage() {
        return this.toString();
    }
}
