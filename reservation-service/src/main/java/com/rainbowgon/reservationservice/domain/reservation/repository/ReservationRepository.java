package com.rainbowgon.reservationservice.domain.reservation.repository;

import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByMemberId(String memberId);
}
