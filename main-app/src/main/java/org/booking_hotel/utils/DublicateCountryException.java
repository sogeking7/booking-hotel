package org.booking_hotel.utils;

public class DublicateCountryException extends RuntimeException {
    private  final int status;
    private  final String description;


    public DublicateCountryException(int status, String message, String description) {

        super(message);
        this.status = status;
        this.description = description;
    }
    public int getStatus() { return status;}
    public String getDescription() { return description;}
}
