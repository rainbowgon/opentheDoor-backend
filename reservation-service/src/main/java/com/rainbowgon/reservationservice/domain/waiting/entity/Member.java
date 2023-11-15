package com.rainbowgon.reservationservice.domain.waiting.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("Member")
public class Member {

    @Id
    private String memberId;

    private Set<String> waitingIdSet;

    public Member(String memberId) {
        this.memberId = memberId;
        this.waitingIdSet = new HashSet<>();
    }
}
