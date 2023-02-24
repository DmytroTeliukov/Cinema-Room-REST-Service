package com.dteliukov.cinemarestservice.exception;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException() {
        super("The password is wrong!");
    }
}
