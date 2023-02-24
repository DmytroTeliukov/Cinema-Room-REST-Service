package com.dteliukov.cinemarestservice.model.common;

import lombok.Data;

public record Seat(
    int row,
    int column
) {}
