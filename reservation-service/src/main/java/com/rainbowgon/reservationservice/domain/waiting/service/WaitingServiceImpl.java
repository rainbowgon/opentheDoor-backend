package com.rainbowgon.reservationservice.domain.waiting.service;

import com.rainbowgon.reservationservice.domain.waiting.dto.request.WaitingReqDto;
import com.rainbowgon.reservationservice.domain.waiting.entity.Member;
import com.rainbowgon.reservationservice.domain.waiting.entity.Waiting;
import com.rainbowgon.reservationservice.domain.waiting.repository.MemberRedisRepository;
import com.rainbowgon.reservationservice.domain.waiting.repository.WaitingRedisRepository;
import com.rainbowgon.reservationservice.global.client.SearchServiceClient;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeOriginalInfoInDto;
import com.rainbowgon.reservationservice.global.error.exception.WaitingAlreadyExistsException;
import com.rainbowgon.reservationservice.global.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WaitingServiceImpl implements WaitingService {

    private final MemberRedisRepository memberRedisRepository;
    private final WaitingRedisRepository waitingRedisRepository;
    private final SearchServiceClient searchServiceClient;

    @Override
    @Transactional
    public void waitEmptyTimeSlot(String memberId, WaitingReqDto waitingReqDto) {

        ThemeOriginalInfoInDto originalInfo = searchServiceClient.getOriginalInfo(waitingReqDto.getThemeId());
        String waitingId = RedisUtil.createWaitingKey(originalInfo.getTitle(),
                                                      originalInfo.getOriginalPoster(),
                                                      waitingReqDto.getTargetDate(),
                                                      waitingReqDto.getTargetTime());

        Waiting waiting = waitingRedisRepository.findById(waitingId)
                .orElseGet(() -> waitingReqDto.toEntity(waitingId));

        if (waiting.getMemberIdSet().contains(memberId)) {
            throw WaitingAlreadyExistsException.EXCEPTION;
        }

        Member member = memberRedisRepository.findById(memberId)
                .orElseGet(() -> new Member(memberId));

        waiting.getMemberIdSet().add(memberId);
        member.getWaitingIdSet().add(waitingId);

        waitingRedisRepository.save(waiting);
        memberRedisRepository.save(member);
    }

    @Override
    @Transactional
    public void cancelWaiting(String memberId, WaitingReqDto waitingReqDto) {

        // TODO 레디스에 예약 대기 정보 삭제
    }
}
