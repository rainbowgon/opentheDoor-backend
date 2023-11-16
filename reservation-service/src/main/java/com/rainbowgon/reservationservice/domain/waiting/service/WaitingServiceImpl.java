package com.rainbowgon.reservationservice.domain.waiting.service;

import com.rainbowgon.reservationservice.domain.waiting.dto.request.EmptyTimeSlotReqDto;
import com.rainbowgon.reservationservice.domain.waiting.dto.request.WaitingReqDto;
import com.rainbowgon.reservationservice.domain.waiting.entity.Member;
import com.rainbowgon.reservationservice.domain.waiting.entity.Waiting;
import com.rainbowgon.reservationservice.domain.waiting.repository.MemberRedisRepository;
import com.rainbowgon.reservationservice.domain.waiting.repository.WaitingRedisRepository;
import com.rainbowgon.reservationservice.global.client.MemberServiceClient;
import com.rainbowgon.reservationservice.global.client.NotificationServiceClient;
import com.rainbowgon.reservationservice.global.client.SearchServiceClient;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeOriginalInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.output.EmptyTimeSlotNotificationOutDto;
import com.rainbowgon.reservationservice.global.client.dto.output.MemberIdOutDto;
import com.rainbowgon.reservationservice.global.error.exception.WaitingAlreadyExistsException;
import com.rainbowgon.reservationservice.global.error.exception.WaitingHistoryNotFoundException;
import com.rainbowgon.reservationservice.global.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WaitingServiceImpl implements WaitingService {

    private final MemberRedisRepository memberRedisRepository;
    private final WaitingRedisRepository waitingRedisRepository;
    private final SearchServiceClient searchServiceClient;
    private final NotificationServiceClient notificationServiceClient;
    private final MemberServiceClient memberServiceClient;

    @Override
    @Transactional
    public void waitEmptyTimeSlot(String memberId, WaitingReqDto waitingReqDto) {

        String waitingId = getWaitingId(waitingReqDto);

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

        Member member = memberRedisRepository.findById(memberId)
                .orElseThrow(WaitingHistoryNotFoundException::new);
        Set<String> waitingIdSet = member.getWaitingIdSet();

        String waitingId = getWaitingId(waitingReqDto);

        if (!waitingIdSet.contains(waitingId)) {
            throw WaitingHistoryNotFoundException.EXCEPTION;
        }

        Waiting waiting = waitingRedisRepository.findById(waitingId)
                .orElseThrow(WaitingHistoryNotFoundException::new);

        Set<String> memberIdSet = waiting.getMemberIdSet();

        if (!memberIdSet.contains(memberId)) {
            throw WaitingHistoryNotFoundException.EXCEPTION;
        }

        waitingIdSet.remove(waitingId);
        memberIdSet.remove(memberId);

        memberRedisRepository.save(member);
        waitingRedisRepository.save(waiting);

    }

    @Override
    public void notifyEmptyTimeSlot(EmptyTimeSlotReqDto emptyTimeSlotReqDto) {
        String waitingId = getWaitingId(emptyTimeSlotReqDto);

        waitingRedisRepository.findById(waitingId).ifPresent((waiting) -> {
            List<EmptyTimeSlotNotificationOutDto> emptyTimeSlotNotificationOutDtoList =
                    getEmptyTimeSlotNotificationOutDtoList(waiting);

            notificationServiceClient.notifyEmptyTimeSlot(emptyTimeSlotNotificationOutDtoList);
        });
    }

    private List<EmptyTimeSlotNotificationOutDto> getEmptyTimeSlotNotificationOutDtoList(Waiting waiting) {
        ThemeBriefInfoInDto themeInfo = searchServiceClient.getThemeBriefInfo(waiting.getThemeId());

        List<MemberIdOutDto> memberIdOutDtoList =
                waiting.getMemberIdSet().stream().map(MemberIdOutDto::from).collect(Collectors.toList());

        return memberServiceClient.getFcmTokenList(memberIdOutDtoList).stream()
                .map(fcmTokenInDto -> EmptyTimeSlotNotificationOutDto
                        .from(fcmTokenInDto, themeInfo, waiting))
                .collect(Collectors.toList());
    }


    private String getWaitingId(WaitingReqDto waitingReqDto) {
        ThemeOriginalInfoInDto originalInfo = searchServiceClient.getOriginalInfo(waitingReqDto.getThemeId());
        String timeLineId = RedisUtil.createTimeLineId(originalInfo.getTitle());
        return RedisUtil.createWaitingId(timeLineId,
                                         waitingReqDto.getTargetDate(),
                                         waitingReqDto.getTargetTime());
    }

    private String getWaitingId(EmptyTimeSlotReqDto emptyTimeSlotReqDto) {
        return RedisUtil.createWaitingId(emptyTimeSlotReqDto.getTimeLineId(),
                                         emptyTimeSlotReqDto.getTargetDate(),
                                         emptyTimeSlotReqDto.getTargetTime());
    }

}
