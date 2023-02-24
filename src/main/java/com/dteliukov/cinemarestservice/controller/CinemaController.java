package com.dteliukov.cinemarestservice.controller;

import com.dteliukov.cinemarestservice.model.response.RoomInfo;
import com.dteliukov.cinemarestservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CinemaController {
    @Autowired
    private final RoomService roomService;

    @GetMapping("/seats")
    public RoomInfo getRoom() {
        return roomService.getRoomInfo();
    }
}
