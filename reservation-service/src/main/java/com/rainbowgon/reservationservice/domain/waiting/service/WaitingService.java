package com.rainbowgon.reservationservice.domain.waiting.service;

import com.rainbowgon.reservationservice.domain.waiting.repository.WaitingRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRedisRepository waitingRedisRepository;
}
