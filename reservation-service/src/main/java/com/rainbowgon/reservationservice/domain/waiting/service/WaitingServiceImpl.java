package com.rainbowgon.reservationservice.domain.waiting.service;

import com.rainbowgon.reservationservice.domain.reservation.dto.request.WaitingReqDto;
import com.rainbowgon.reservationservice.domain.waiting.repository.WaitingRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingServiceImpl implements WaitingService {

    private final WaitingRedisRepository waitingRedisRepository;

    @Override
    public void waitEmptyTimeSlot(String memberId, WaitingReqDto waitingReqDto) {

        // TODO 레디스에 예약 대기 정보 저장
    }

    @Override
    public void cancelWaiting(String memberId, WaitingReqDto waitingReqDto) {

        // TODO 레디스에 예약 대기 정보 삭제
    }
}
