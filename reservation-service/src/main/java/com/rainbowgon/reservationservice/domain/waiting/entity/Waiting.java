package com.rainbowgon.reservationservice.domain.waiting.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

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
    private LocalDate targetDate;
    private LocalTime targetTime;

}
