package com.rainbowgon.reservationservice.domain.reservation.repository;

import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
