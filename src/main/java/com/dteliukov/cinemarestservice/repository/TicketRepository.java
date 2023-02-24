package com.dteliukov.cinemarestservice.repository;

import com.dteliukov.cinemarestservice.model.common.PurchasedTicket;
import com.dteliukov.cinemarestservice.model.common.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class TicketRepository {
    private final Map<String, Ticket> tickets;

    public PurchasedTicket save(Ticket ticket) {
        String token = UUID.randomUUID().toString();
        tickets.put(token, ticket);
        return new PurchasedTicket(token, ticket);
    }

    public void delete(String token) {
        tickets.remove(token);
    }
    public Optional<Ticket> getTicketByToken(String token) {
        return Optional.ofNullable(tickets.get(token));
    }
}
