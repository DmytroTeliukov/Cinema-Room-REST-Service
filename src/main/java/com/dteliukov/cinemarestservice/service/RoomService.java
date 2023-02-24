package com.dteliukov.cinemarestservice.service;

import com.dteliukov.cinemarestservice.model.common.Room;
import com.dteliukov.cinemarestservice.model.response.RoomInfo;
import com.dteliukov.cinemarestservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
    @Autowired
    private final RoomRepository roomRepository;

    public RoomInfo getRoomInfo() {
        Room room = roomRepository.getRoom();
        return new RoomInfo(room.getTotalRows(), room.getTotalColumns(), room.getSeats().stream().toList());
    }
}
