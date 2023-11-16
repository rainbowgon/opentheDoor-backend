package com.rainbowgon.reservationservice.domain.timeslot.service;

import com.rainbowgon.reservationservice.global.vo.TimeSlotVO;

import java.util.List;

public interface TimeLineService {

    List<TimeSlotVO> getThemeTimeLine(String themeId);
}
