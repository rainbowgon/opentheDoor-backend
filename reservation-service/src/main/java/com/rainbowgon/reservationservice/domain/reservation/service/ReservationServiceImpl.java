package com.rainbowgon.reservationservice.domain.reservation.service;

import com.rainbowgon.reservationservice.domain.reservation.dto.request.ReservationReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBaseInfoResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationResultResDto;
import com.rainbowgon.reservationservice.domain.reservation.repository.ReservationRepository;
import com.rainbowgon.reservationservice.global.client.MemberServiceClient;
import com.rainbowgon.reservationservice.global.client.SearchServiceClient;
import com.rainbowgon.reservationservice.global.client.dto.input.MemberBriefInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberServiceClient memberServiceClient;
    private final SearchServiceClient searchServiceClient;

    public ReservationBaseInfoResDto getReservationBaseInfo(String memberId, String themeId) {
        MemberBriefInfoInDto memberInfoForReservation =
                memberServiceClient.getMemberBriefInfo(memberId);
        ThemeBriefInfoInDto themeInfoForReservation =
                searchServiceClient.getThemeBriefInfo(themeId);

        // TODO TimeSlotList 정보 가져오기

        return ReservationBaseInfoResDto.from(themeId, memberInfoForReservation, themeInfoForReservation);
    }

    @Override
    public ReservationBaseInfoResDto getReservationBaseInfo(String themeId) {
        ThemeBriefInfoInDto themeInfoForReservation =
                searchServiceClient.getThemeBriefInfo(themeId);

        // TODO TimeSlotList 정보 가져오기

        return ReservationBaseInfoResDto.from(themeId, themeInfoForReservation);
    }

    /**
     * 검증
     * 1. 원하는 테아의 원하는 날짜, 시간대가 비어있는지 확인
     * 2. 회원인 경우, 저장된 이름과 전화번호가 맞는지
     * 3. 존재하는 themeId인지
     * 4. 테마, 인원수(headcount)에 알맞는 가격(totalPrice)인지
     */
    @Override
    public ReservationResultResDto makeReservation(String memberId, ReservationReqDto reservationReqDto) {

        memberServiceClient.getMemberBriefInfo()


        return null;
    }
}
