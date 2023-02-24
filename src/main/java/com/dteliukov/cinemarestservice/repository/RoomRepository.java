package com.dteliukov.cinemarestservice.repository;

import com.dteliukov.cinemarestservice.model.common.Room;
import com.dteliukov.cinemarestservice.model.common.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RoomRepository {
    @Autowired
    private final Room room;

    public Room getRoom() {
        return room;
    }

    public boolean isExistSeat(Seat seat) {
        return room.getSeats().contains(seat);
    }

    public void deleteSeat(Seat seat) {
        room.getSeats().remove(seat);
    }

    public void addSeat(Seat seat) { room.getSeats().add(seat); }
}

