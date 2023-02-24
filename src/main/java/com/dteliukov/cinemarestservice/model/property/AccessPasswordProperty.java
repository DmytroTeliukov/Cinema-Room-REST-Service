package com.dteliukov.cinemarestservice.model.property;

public record AccessPasswordProperty(String secretPassword) {
    public boolean isValid(String password) {
        return secretPassword.equals(password);
    }
}
