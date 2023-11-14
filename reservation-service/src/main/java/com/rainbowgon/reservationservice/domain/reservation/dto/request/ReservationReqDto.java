package com.rainbowgon.reservationservice.domain.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.reservationservice.domain.reservation.entity.MemberVerifiedStatus;
import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationReqDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    @JsonFormat(pattern = "hh:mm")
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

    public Reservation toUnauthEntity(Integer totalPrice) {
        return Reservation.builder()
                .targetDate(targetDate)
                .targetTime(targetTime)
                .headcount(headcount)
                .totalPrice(totalPrice)
                .isMemberVerified(MemberVerifiedStatus.NOT_VERIFIED)
                .bookerName(bookerName)
                .bookerPhoneNumber(bookerPhoneNumber)
                .themeId(themeId)
                .memberId(null)
                .build();
    }
}
