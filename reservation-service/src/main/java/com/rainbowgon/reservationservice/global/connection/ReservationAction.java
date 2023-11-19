package com.rainbowgon.reservationservice.global.connection;

import com.rainbowgon.reservationservice.global.connection.dto.ReservingServerRequestDto;

public interface ReservationAction {

    Boolean reserve(ReservingServerRequestDto reservingServerRequestDto);

}
