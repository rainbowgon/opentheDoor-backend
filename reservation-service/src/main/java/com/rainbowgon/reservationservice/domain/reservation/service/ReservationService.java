package com.rainbowgon.reservationservice.domain.reservation.service;

import com.rainbowgon.reservationservice.domain.reservation.dto.request.ReservationReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.request.UnauthReservationDetailReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.request.WaitingReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBaseInfoResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBriefResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationDetailResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationResultResDto;

import java.util.List;

public interface ReservationService {

    ReservationBaseInfoResDto getReservationBaseInfo(String memberId, String themeId);

    ReservationBaseInfoResDto getReservationBaseInfo(String themeId);

    ReservationResultResDto makeReservation(String memberId, ReservationReqDto reservationReqDto);

    ReservationResultResDto makeReservation(ReservationReqDto reservationReqDto);

    List<ReservationBriefResDto> getAllReservationHistory(String memberId);

    ReservationDetailResDto getReservationDetail(String memberId, Long reservationId);

    ReservationDetailResDto getReservationDetail(UnauthReservationDetailReqDto unauthReservationDetailReqDto);

    void waitEmptyTimeSlot(String memberId, WaitingReqDto waitingReqDto);

    void cancelWaiting(String memberId, WaitingReqDto waitingReqDto);
}
