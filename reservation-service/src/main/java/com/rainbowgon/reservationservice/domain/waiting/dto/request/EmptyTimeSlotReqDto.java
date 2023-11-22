package com.rainbowgon.reservationservice.domain.waiting.dto.request;

import lombok.Getter;

@Getter
public class EmptyTimeSlotReqDto {

    private String timeLineId;
    private String targetDate;
    private String targetTime;

}
