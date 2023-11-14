package com.rainbowgon.reservationservice.domain.reservation.service;

import com.rainbowgon.reservationservice.domain.reservation.dto.request.ReservationReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBaseInfoResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationResultResDto;
import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import com.rainbowgon.reservationservice.domain.reservation.repository.ReservationRepository;
import com.rainbowgon.reservationservice.global.client.MemberServiceClient;
import com.rainbowgon.reservationservice.global.client.SearchServiceClient;
import com.rainbowgon.reservationservice.global.client.dto.input.MemberBriefInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
import com.rainbowgon.reservationservice.global.error.exception.BookerInfoInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 1. 원하는 테마의 원하는 날짜, 시간대가 비어있는지 확인 -> 이미 예약되어 있으면 Fail
     * 2. 회원인 경우, 저장된 이름과 전화번호가 맞는지 (checked)
     * 3. 존재하는 themeId인지 -> 아니면 Exception
     */
    @Override
    @Transactional
    public ReservationResultResDto makeReservation(String memberId, ReservationReqDto reservationReqDto) {


        validateBookerInfo(memberId, reservationReqDto);

        // TODO 예약 기능 동작
        boolean isSucceed = actReservation(memberId);

        if (!isSucceed) {
            return ReservationResultResDto.fail();
        }

        Integer totalPrice = searchServiceClient.getTotalPrice(reservationReqDto.getThemeId(),
                                                               reservationReqDto.getHeadcount());

        Reservation reservation = reservationReqDto.toAuthEntity(memberId, totalPrice);
        reservationRepository.save(reservation);
        reservation.updateReservationNumber();

        return ReservationResultResDto.success(reservation.getId(), reservation.getReservationNumber(),
                                               totalPrice);
    }

    // TODO 예약 기능 동작
    private boolean actReservation(String memberId) {

        return true;
    }

    private void validateBookerInfo(String memberId, ReservationReqDto reservationReqDto) {
        MemberBriefInfoInDto memberBriefInfo = memberServiceClient.getMemberBriefInfo(memberId);
        if (!reservationReqDto.getBookerName().equals(memberBriefInfo.getBookerName()) || !reservationReqDto.getBookerPhoneNumber().equals(memberBriefInfo.getBookerPhoneNumber())) {
            throw BookerInfoInvalidException.EXCEPTION;
        }
    }
}
