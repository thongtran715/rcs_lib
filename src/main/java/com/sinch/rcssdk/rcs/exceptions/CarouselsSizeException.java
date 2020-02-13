package com.sinch.rcssdk.rcs.exceptions;

public class CarouselsSizeException extends Exception {
    private int x;

    public CarouselsSizeException(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "CarouselsSizeException{" +
                "x=" + x +
                '}' + " . Carousels need to be in between 2 to 10 rich card";
    }

    @Override
    public String getMessage() {
        return this.toString();
    }
}
