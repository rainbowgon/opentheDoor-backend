package com.rainbowgon.searchservice.global.client.dto.input;

import com.rainbowgon.searchservice.global.client.dto.input.entry.TimeEntry;
import lombok.Getter;

import java.util.List;

@Getter
public class ReservationInDto {

    //    @JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDate date;
    private List<TimeEntry> timeList;
}
