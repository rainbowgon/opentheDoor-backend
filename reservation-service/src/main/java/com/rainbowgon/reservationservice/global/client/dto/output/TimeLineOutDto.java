package com.rainbowgon.reservationservice.global.client.dto.output;

import com.rainbowgon.reservationservice.global.vo.TimeSlotVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class TimeLineOutDto {

    List<TimeSlotVO> timeSlotList;

    public static TimeLineOutDto from(List<TimeSlotVO> timeSlotList) {
        return TimeLineOutDto.builder()
                .timeSlotList(timeSlotList)
                .build();
    }
}
