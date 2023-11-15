package com.rainbowgon.reservationservice.domain.waiting.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("Member")
public class Member {

    @Id
    private String memberId;

    private List<String> waitingIdList;

    public Member(String memberId) {
        this.memberId = memberId;
        this.waitingIdList = new ArrayList<>();
    }
}
