package com.dteliukov.cinemarestservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Stats(@JsonProperty("current_income") int income,
                    @JsonProperty("number_of_available_seats") int numberAvailableSeats,
                    @JsonProperty("number_of_purchased_seats") int numberPurchasedTickets) { }
