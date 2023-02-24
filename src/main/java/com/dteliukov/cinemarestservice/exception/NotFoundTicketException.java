package com.dteliukov.cinemarestservice.exception;

public class NotFoundTicketException extends RuntimeException {
    public NotFoundTicketException() {
        super("Wrong token!");
    }
}
