package com.dteliukov.cinemarestservice.config;

import com.dteliukov.cinemarestservice.model.common.Room;
import com.dteliukov.cinemarestservice.model.common.Seat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Configuration
public class CinemaConfiguration {
    @Value("${cinema.rows}")
    private int rows;

    @Value("${cinema.columns}")
    private int columns;

    @Bean
    public Room getRoom() {
        return new Room(rows, columns, 0,
                rows * columns, 0, generateSeats());
    }

    private Set<Seat> generateSeats() {
        Set<Seat> seats = new TreeSet<>(Comparator
                .comparing(Seat::row)
                .thenComparing(Seat::column));

        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                seats.add(new Seat(row, column));
            }
        }

        return seats;
    }
}
