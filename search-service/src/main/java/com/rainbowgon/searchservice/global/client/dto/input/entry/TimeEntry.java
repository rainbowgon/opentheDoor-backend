package com.rainbowgon.searchservice.global.client.dto.input.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimeEntry {

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private AvailableStatus isAvailable;
}