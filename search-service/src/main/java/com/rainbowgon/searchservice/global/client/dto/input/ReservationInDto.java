package com.rainbowgon.searchservice.global.client.dto.input;

import com.rainbowgon.searchservice.global.client.dto.input.entry.TimeEntry;
import lombok.Getter;

import java.util.List;

@Getter
public class ReservationInDto {

    private String date;
    private List<TimeEntry> timeList;
}
