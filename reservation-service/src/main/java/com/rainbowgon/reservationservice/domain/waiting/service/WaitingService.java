package com.rainbowgon.reservationservice.domain.waiting.service;

import com.rainbowgon.reservationservice.domain.waiting.dto.request.WaitingReqDto;

public interface WaitingService {

    void waitEmptyTimeSlot(String memberId, WaitingReqDto waitingReqDto);

    void cancelWaiting(String memberId, WaitingReqDto waitingReqDto);
}
