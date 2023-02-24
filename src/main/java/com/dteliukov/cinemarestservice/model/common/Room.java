package com.dteliukov.cinemarestservice.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private int totalRows;
    private int totalColumns;
    private int income;
    private int numberAvailableSeats;
    private int numberPurchasedSeats;
    private Set<Seat> seats;
}
