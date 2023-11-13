package com.rainbowgon.reservationservice.domain.reservation.service;

import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBaseInfoResDto;
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
                memberServiceClient.getMemberInfoForReservation(memberId);
        ThemeBriefInfoInDto themeInfoForReservation =
                searchServiceClient.getThemeInfoForReservation(themeId);

        // TODO TimeSlotList 정보 가져오기

        return ReservationBaseInfoResDto.from(themeId, memberInfoForReservation, themeInfoForReservation);
    }
}
