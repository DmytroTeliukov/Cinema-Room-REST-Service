package com.dteliukov.cinemarestservice.controller;

import com.dteliukov.cinemarestservice.exception.AlreadyPurchasedTicketException;
import com.dteliukov.cinemarestservice.exception.NotFoundTicketException;
import com.dteliukov.cinemarestservice.exception.SeatOutOfBoundsException;
import com.dteliukov.cinemarestservice.exception.WrongPasswordException;
import com.dteliukov.cinemarestservice.model.common.PurchasedTicket;
import com.dteliukov.cinemarestservice.model.common.Seat;
import com.dteliukov.cinemarestservice.model.common.Ticket;
import com.dteliukov.cinemarestservice.model.request.Token;
import com.dteliukov.cinemarestservice.model.response.RoomInfo;
import com.dteliukov.cinemarestservice.model.response.Stats;
import com.dteliukov.cinemarestservice.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CinemaController {
    @Autowired
    private final RoomService roomService;

    @GetMapping("/seats")
    public RoomInfo getRoom() {
        return roomService.getRoomInfo();
    }

    @PostMapping("/purchase")
    public PurchasedTicket purchase(@RequestBody Seat seat) {
        return roomService.purchase(seat);
    }

    @PostMapping("/return")
    public Ticket returnTicket(@RequestBody Token token) {
        return roomService.returnTicket(token);
    }

    @PostMapping("/stats")
    public Stats getStats(@RequestParam(required = false) String password) {
        return roomService.getRoomStats(password);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<?> handlerWrongPassword(WrongPasswordException e) {
        return new ResponseEntity<>(errorMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundTicketException.class)
    public ResponseEntity<?> handlerNotFoundTicket(NotFoundTicketException e) {
        return new ResponseEntity<>(errorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SeatOutOfBoundsException.class)
    public ResponseEntity<?> handlerSeatOutOfBoundsException(SeatOutOfBoundsException e) {
        return new ResponseEntity<>(errorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyPurchasedTicketException.class)
    public ResponseEntity<?> handlerAlreadyPurchasedTicket(AlreadyPurchasedTicketException e) {
        return new ResponseEntity<>(errorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> errorMessage(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("error", message);
        return body;
    }
}
