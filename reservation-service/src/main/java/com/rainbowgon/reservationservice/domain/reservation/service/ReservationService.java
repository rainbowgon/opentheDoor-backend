package com.rainbowgon.reservationservice.domain.reservation.service;

import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBaseInfoResDto;

public interface ReservationService {

    ReservationBaseInfoResDto getReservationBaseInfo(String memberId, String themeId);
}
