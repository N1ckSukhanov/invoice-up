package com.pack.repository;

import com.pack.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, LocalDate> {
    @Query(value = """
            SELECT r
            FROM Reservation r
            WHERE r.payDate between ?1 and ?2
            """)
    List<Reservation> findReservationsForCurrentAndAdjacentMonths(LocalDate from, LocalDate to);
}