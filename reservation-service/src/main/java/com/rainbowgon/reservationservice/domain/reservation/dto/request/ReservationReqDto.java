package com.rainbowgon.reservationservice.domain.reservation.dto.request;

import com.rainbowgon.reservationservice.domain.reservation.entity.MemberVerifiedStatus;
import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationReqDto {

    private LocalDate targetDate;
    private LocalTime targetTime;
    private Integer headcount;
    private String bookerName;
    private String bookerPhoneNumber;
    private String themeId;

    public Reservation toAuthEntity(String memberId, Integer totalPrice) {
        return Reservation.builder()
                .targetDate(targetDate)
                .targetTime(targetTime)
                .headcount(headcount)
                .totalPrice(totalPrice)
                .isMemberVerified(MemberVerifiedStatus.VERIFIED)
                .bookerName(bookerName)
                .bookerPhoneNumber(bookerPhoneNumber)
                .themeId(themeId)
                .memberId(memberId)
                .build();
    }

}
