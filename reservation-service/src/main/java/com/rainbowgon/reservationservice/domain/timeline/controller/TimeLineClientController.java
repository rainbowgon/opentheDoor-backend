package com.rainbowgon.reservationservice.domain.timeline.controller;

import com.rainbowgon.reservationservice.domain.timeline.service.TimeLineService;
import com.rainbowgon.reservationservice.global.client.dto.output.TimeLineOutDto;
import com.rainbowgon.reservationservice.global.vo.TimeSlotVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients/timelines")
public class TimeLineClientController {

    private final TimeLineService timeLineService;

    @GetMapping("/{theme-id}")
    public ResponseEntity<TimeLineOutDto> getThemeTimeLine(@PathVariable("theme-id") String themeId) {
        List<TimeSlotVO> themeTimeLine = timeLineService.getThemeTimeLine(themeId);
        return ResponseEntity.ok(TimeLineOutDto.from(themeTimeLine));
    }
}
