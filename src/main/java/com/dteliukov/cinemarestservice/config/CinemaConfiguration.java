package com.dteliukov.cinemarestservice.config;

import com.dteliukov.cinemarestservice.model.common.Room;
import com.dteliukov.cinemarestservice.model.common.Seat;
import com.dteliukov.cinemarestservice.model.property.AccessPasswordProperty;
import com.dteliukov.cinemarestservice.model.property.PriceRangeProperty;
import com.dteliukov.cinemarestservice.model.property.SeatRangeProperty;
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

    @Value("${cinema.first-price}")
    private int firstPrice;

    @Value("${cinema.second-price}")
    private int secondPrice;

    @Value("${cinema.border-first-row}")
    private int borderFirstRow;

    @Value("${cinema.password}")
    private String secretPassword;

    @Bean
    public Room getRoom() {
        return new Room(rows, columns, 0,
                rows * columns, 0, generateSeats());
    }

    @Bean(name = "row_seat_range")
    public SeatRangeProperty getRowSeatRangeProperty() {
        return new SeatRangeProperty(0, rows);
    }

    @Bean(name = "column_seat_range")
    public SeatRangeProperty getColumnSeatRangeProperty() {
        return new SeatRangeProperty(0, columns);
    }

    @Bean
    public PriceRangeProperty getPriceRangeProperty() {
        return new PriceRangeProperty(firstPrice, secondPrice, borderFirstRow);
    }

    @Bean
    public AccessPasswordProperty getAccessPasswordProperty() {
        return new AccessPasswordProperty(secretPassword);
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
