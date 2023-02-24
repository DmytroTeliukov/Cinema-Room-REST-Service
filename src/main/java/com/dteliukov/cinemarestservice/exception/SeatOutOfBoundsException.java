package com.dteliukov.cinemarestservice.exception;

public class SeatOutOfBoundsException extends RuntimeException {
    public SeatOutOfBoundsException() {
        super("The number of a row or a column is out of bounds!");
    }
}
