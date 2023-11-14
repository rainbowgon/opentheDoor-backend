package com.rainbowgon.reservationservice.domain.reservation.repository;

import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByMemberId(String memberId);

    Optional<Reservation> findByMemberIdAndThemeId(String memberId, String themeId);
}
