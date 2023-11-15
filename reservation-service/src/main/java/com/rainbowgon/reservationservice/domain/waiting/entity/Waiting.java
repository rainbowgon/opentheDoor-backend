package com.rainbowgon.reservationservice.domain.waiting.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("Waiting")
public class Waiting {

    @Id
    private String themeKey;

    @Indexed
    private String memberId;

    private String themeId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime targetTime;

}
