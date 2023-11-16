package com.rainbowgon.reservationservice.domain.timeslot.service;

import com.rainbowgon.reservationservice.domain.timeslot.repository.TimeLineRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeLineServiceImpl implements TimeLineService {

    private final TimeLineRedisRepository timeLineRedisRepository;
}
