package com.rainbowgon.reservationservice.domain.timeline.entity;

import com.rainbowgon.reservationservice.global.vo.TimeSlotVO;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@RedisHash("TimeLine")
public class TimeLine {

    @Id
    private String timeLineId;

    private List<TimeSlotVO> timeSlotList;
}
