package com.rainbowgon.reservationservice.domain.waiting.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

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

    private String targetDate;

    private String targetTime;

    @Builder
    public Waiting(String waitingId, String themeId, String targetDate, String targetTime) {
        this.waitingId = waitingId;
        this.memberIdSet = new HashSet<>();
        this.themeId = themeId;
        this.targetDate = targetDate;
        this.targetTime = targetTime;
    }

}
