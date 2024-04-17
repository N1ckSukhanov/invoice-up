package com.pack.service;

import com.pack.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<ReservationDTO> getReservations(LocalDate from, LocalDate to) {
        return repository.findReservationsForCurrentAndAdjacentMonths(from, to).stream()
                .map(r -> new ReservationDTO(
                        r.getTaskCountMax().toString(),
                        r.getPayDate(),
                        r.getPayDate().plusDays(1),
                        true
                )).toList();
    }

    public record ReservationDTO(
            String title,
            LocalDate start,
            LocalDate end,
            boolean allDay
    ) {
    }
}
