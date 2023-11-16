package com.rainbowgon.reservationservice.global.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class TimeSlotVO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private List<TimeVO> timeList;
}
