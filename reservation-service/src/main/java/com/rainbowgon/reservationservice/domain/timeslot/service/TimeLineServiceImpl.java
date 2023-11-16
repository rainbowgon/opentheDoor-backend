package com.rainbowgon.reservationservice.domain.timeslot.service;

import com.rainbowgon.reservationservice.domain.timeslot.entity.TimeLine;
import com.rainbowgon.reservationservice.domain.timeslot.repository.TimeLineRedisRepository;
import com.rainbowgon.reservationservice.global.client.SearchServiceClient;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeOriginalInfoInDto;
import com.rainbowgon.reservationservice.global.error.exception.TimeLineNotFoundException;
import com.rainbowgon.reservationservice.global.utils.RedisUtil;
import com.rainbowgon.reservationservice.global.vo.TimeSlotVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeLineServiceImpl implements TimeLineService {

    private final TimeLineRedisRepository timeLineRedisRepository;
    private final SearchServiceClient searchServiceClient;

    public List<TimeSlotVO> getThemeTimeLine(String themeId) {
        ThemeOriginalInfoInDto originalInfo = searchServiceClient.getOriginalInfo(themeId);
        String targetTimeLineId = RedisUtil.createTimeLineId(originalInfo.getTitle(),
                                                             originalInfo.getOriginalPoster());

        TimeLine timeLine = timeLineRedisRepository.findById(targetTimeLineId)
                .orElseThrow(TimeLineNotFoundException::new);

        return timeLine.getTimeSlotList();
    }
}
