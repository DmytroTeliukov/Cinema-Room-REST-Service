package com.dteliukov.cinemarestservice.exception;

public class AlreadyPurchasedTicketException extends RuntimeException {

    public AlreadyPurchasedTicketException() {
        super("The ticket has been already purchased!");
    }
}
