package com.rainbowgon.reservationservice.global.vo;

import lombok.Getter;

import java.util.List;

@Getter
public class TimeSlotVO {

    private String date;
    private List<TimeVO> timeList;
}
