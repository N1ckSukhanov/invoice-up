package com.pack.controllers;

import com.pack.service.ReservationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/api/reservations")
    public List<ReservationService.ReservationDTO> getReservations(
            @RequestParam("from") String from,
            @RequestParam("to") String to
    ) {
        var format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        return reservationService.getReservations(
                OffsetDateTime.parse(from, format).toLocalDate(),
                OffsetDateTime.parse(to, format).toLocalDate()
        );
    }
}
