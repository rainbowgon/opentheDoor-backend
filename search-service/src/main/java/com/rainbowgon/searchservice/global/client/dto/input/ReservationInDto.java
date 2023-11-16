package com.rainbowgon.searchservice.global.client.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.searchservice.global.client.dto.input.entry.TimeEntry;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ReservationInDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private List<TimeEntry> timeList;
}
