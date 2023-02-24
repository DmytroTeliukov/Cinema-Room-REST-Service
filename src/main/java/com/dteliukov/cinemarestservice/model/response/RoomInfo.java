package com.dteliukov.cinemarestservice.model.response;

import com.dteliukov.cinemarestservice.model.common.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RoomInfo(@JsonProperty("total_rows") int totalRows,
                       @JsonProperty("total_columns") int totalColumns,
                       @JsonProperty("available_seats") List<Seat> seats) {
}
