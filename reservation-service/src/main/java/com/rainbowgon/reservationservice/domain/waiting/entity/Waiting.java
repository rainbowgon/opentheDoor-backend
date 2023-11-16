package com.rainbowgon.reservationservice.domain.waiting.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("Waiting")
public class Waiting {

    @Id
    private String waitingId;

    private Set<String> memberIdSet;

    private String themeId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime targetTime;

    @Builder
    public Waiting(String waitingId, String themeId, LocalDate targetDate, LocalTime targetTime) {
        this.waitingId = waitingId;
        this.memberIdSet = new HashSet<>();
        this.themeId = themeId;
        this.targetDate = targetDate;
        this.targetTime = targetTime;
    }

}