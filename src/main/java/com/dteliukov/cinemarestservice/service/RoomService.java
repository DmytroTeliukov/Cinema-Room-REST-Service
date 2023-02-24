package com.dteliukov.cinemarestservice.service;

import com.dteliukov.cinemarestservice.exception.AlreadyPurchasedTicketException;
import com.dteliukov.cinemarestservice.exception.NotFoundTicketException;
import com.dteliukov.cinemarestservice.exception.SeatOutOfBoundsException;
import com.dteliukov.cinemarestservice.exception.WrongPasswordException;
import com.dteliukov.cinemarestservice.model.common.PurchasedTicket;
import com.dteliukov.cinemarestservice.model.common.Room;
import com.dteliukov.cinemarestservice.model.common.Seat;
import com.dteliukov.cinemarestservice.model.common.Ticket;
import com.dteliukov.cinemarestservice.model.property.AccessPasswordProperty;
import com.dteliukov.cinemarestservice.model.property.PriceRangeProperty;
import com.dteliukov.cinemarestservice.model.property.SeatRangeProperty;
import com.dteliukov.cinemarestservice.model.request.Token;
import com.dteliukov.cinemarestservice.model.response.RoomInfo;
import com.dteliukov.cinemarestservice.model.response.Stats;
import com.dteliukov.cinemarestservice.repository.RoomRepository;
import com.dteliukov.cinemarestservice.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final TicketRepository ticketRepository;
    private final SeatRangeProperty rowSeatRange;
    private final SeatRangeProperty columnSeatRange;
    private final PriceRangeProperty priceRangeProperty;
    private final AccessPasswordProperty accessPasswordProperty;

    @Autowired
    public RoomService(RoomRepository roomRepository,
                       TicketRepository ticketRepository,
                       @Qualifier("column_seat_range") SeatRangeProperty rowSeatRange,
                       @Qualifier("row_seat_range") SeatRangeProperty columnSeatRange,
                       PriceRangeProperty priceRangeProperty, AccessPasswordProperty accessPasswordProperty) {
        this.roomRepository = roomRepository;
        this.ticketRepository = ticketRepository;
        this.rowSeatRange = rowSeatRange;
        this.columnSeatRange = columnSeatRange;
        this.priceRangeProperty = priceRangeProperty;
        this.accessPasswordProperty = accessPasswordProperty;
    }


    public RoomInfo getRoomInfo() {
        Room room = roomRepository.getRoom();
        return new RoomInfo(room.getTotalRows(), room.getTotalColumns(), room.getSeats().stream().toList());
    }

    public PurchasedTicket purchase(Seat seat) {
        validateSeat(seat);
        roomRepository.deleteSeat(seat);
        Ticket ticket = generateTicket(seat);
        roomRepository.updateStats(ticket.price(), true);
        return ticketRepository.save(ticket);
    }

    public Ticket returnTicket(Token token) {
        Ticket ticket = ticketRepository.getTicketByToken(token.token())
                .orElseThrow(NotFoundTicketException::new);
        ticketRepository.delete(token.token());
        roomRepository.addSeat(new Seat(ticket.row(), ticket.column()));
        roomRepository.updateStats(ticket.price(), false);
        return ticket;
    }

    public Stats getRoomStats(String password) {
        if (!accessPasswordProperty.isValid(password))
            throw new WrongPasswordException();

        return roomRepository.getStats();
    }

    private Ticket generateTicket(Seat seat) {
        int price = calcPrice(seat);
        return new Ticket(seat.row(), seat.column(), price);
    }

    private int calcPrice(Seat seat) {
        return (seat.row() <= priceRangeProperty.borderRow()) ?
                priceRangeProperty.firstPrice() : priceRangeProperty.secondPrice();
    }

    private void validateSeat(Seat seat) {
        if (!isValidSeat(seat)) {
            throw new SeatOutOfBoundsException();
        } else if (!roomRepository.isExistSeat(seat)) {
            throw new AlreadyPurchasedTicketException();
        }
    }

    private boolean isValidSeat(Seat seat) {
        return checkSeatRange(seat.row(), rowSeatRange.min(), rowSeatRange.max()) &&
                checkSeatRange(seat.column(), columnSeatRange.min(), columnSeatRange.max());
    }

    private boolean checkSeatRange(int seatNumber, int min, int max) {
        return min < seatNumber && seatNumber <= max;
    }
}
