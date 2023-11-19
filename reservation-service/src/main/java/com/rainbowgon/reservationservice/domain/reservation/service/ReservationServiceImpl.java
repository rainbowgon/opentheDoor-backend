package com.rainbowgon.reservationservice.domain.reservation.service;

import com.rainbowgon.reservationservice.domain.reservation.dto.request.ReservationReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.request.UnauthReservationDetailReqDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBaseInfoResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationBriefResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationDetailResDto;
import com.rainbowgon.reservationservice.domain.reservation.dto.response.ReservationResultResDto;
import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import com.rainbowgon.reservationservice.domain.reservation.repository.ReservationRepository;
import com.rainbowgon.reservationservice.domain.timeline.service.TimeLineService;
import com.rainbowgon.reservationservice.global.client.MemberServiceClient;
import com.rainbowgon.reservationservice.global.client.NotificationServiceClient;
import com.rainbowgon.reservationservice.global.client.SearchServiceClient;
import com.rainbowgon.reservationservice.global.client.dto.input.MemberBriefInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeOriginalInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.output.NotificationOutDto;
import com.rainbowgon.reservationservice.global.connection.MasterkeyReservationAction;
import com.rainbowgon.reservationservice.global.connection.dto.ReservingServerRequestDto;
import com.rainbowgon.reservationservice.global.error.exception.BookerInfoInvalidException;
import com.rainbowgon.reservationservice.global.error.exception.ReservationNotFoundException;
import com.rainbowgon.reservationservice.global.vo.TimeSlotVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeLineService timeLineService;
    private final MemberServiceClient memberServiceClient;
    private final SearchServiceClient searchServiceClient;
    private final NotificationServiceClient notificationServiceClient;
    private final MasterkeyReservationAction reservationAction;

    public ReservationBaseInfoResDto getReservationBaseInfo(String memberId, String themeId) {
        MemberBriefInfoInDto memberInfoForReservation =
                memberServiceClient.getMemberBriefInfo(memberId);
        ThemeBriefInfoInDto themeInfoForReservation =
                searchServiceClient.getThemeBriefInfo(themeId);

        List<TimeSlotVO> timeLine = timeLineService.getThemeTimeLine(themeId);

        return ReservationBaseInfoResDto.from(themeId, timeLine, memberInfoForReservation,
                                              themeInfoForReservation);
    }

    @Override
    public ReservationBaseInfoResDto getReservationBaseInfo(String themeId) {
        ThemeBriefInfoInDto themeInfoForReservation =
                searchServiceClient.getThemeBriefInfo(themeId);

        List<TimeSlotVO> timeLine = timeLineService.getThemeTimeLine(themeId);

        return ReservationBaseInfoResDto.from(themeId, timeLine, themeInfoForReservation);
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

        boolean isSucceed = this.reserveMasterkey(reservationReqDto);
        if (!isSucceed) {
            return ReservationResultResDto.fail();
        }

        Integer totalPrice = searchServiceClient.getTotalPrice(reservationReqDto.getThemeId(),
                                                               reservationReqDto.getHeadcount());

        Reservation reservation = reservationReqDto.toAuthEntity(memberId, totalPrice);
        reservationRepository.save(reservation);
        reservation.updateReservationNumber();

        sendSuccessNotification(memberId, reservationReqDto, reservation);

        return ReservationResultResDto.success(reservation.getId(), reservation.getReservationNumber(),
                                               totalPrice);
    }

    @Override
    @Transactional
    public ReservationResultResDto makeReservation(ReservationReqDto reservationReqDto) {

        boolean isSucceed = this.reserveMasterkey(reservationReqDto);
        if (!isSucceed) {
            return ReservationResultResDto.fail();
        }

        Integer totalPrice = searchServiceClient.getTotalPrice(reservationReqDto.getThemeId(),
                                                               reservationReqDto.getHeadcount());

        Reservation reservation = reservationReqDto.toUnauthEntity(totalPrice);
        reservationRepository.save(reservation);
        reservation.updateReservationNumber();

        return ReservationResultResDto.success(reservation.getId(), reservation.getReservationNumber(),
                                               totalPrice);
    }

    // TODO Pagination
    @Override
    @Transactional
    public List<ReservationBriefResDto> getAllReservationHistory(String memberId) {
        return reservationRepository.findAllByMemberId(memberId).stream().map(reservation -> {
            ThemeBriefInfoInDto themeBriefInfo =
                    searchServiceClient.getThemeBriefInfo(reservation.getThemeId());
            return ReservationBriefResDto.from(reservation, themeBriefInfo);
        }).collect(Collectors.toList());
    }

    @Override
    public ReservationDetailResDto getReservationDetail(String memberId, Long reservationId) {
        Reservation reservation =
                reservationRepository.findByIdAndMemberId(reservationId, memberId).orElseThrow(ReservationNotFoundException::new);

        ThemeBriefInfoInDto themeBriefInfo = searchServiceClient.getThemeBriefInfo(reservation.getThemeId());

        return ReservationDetailResDto.from(reservation, themeBriefInfo);
    }

    @Override
    public ReservationDetailResDto getReservationDetail(UnauthReservationDetailReqDto unauthReservationDetailReqDto) {
        Reservation reservation =
                reservationRepository.findByBookerNameAndBookerPhoneNumberAndReservationNumber(
                        unauthReservationDetailReqDto.getBookerName(),
                        unauthReservationDetailReqDto.getBookerPhoneNumber(),
                        unauthReservationDetailReqDto.getReservationNumber());

        ThemeBriefInfoInDto themeBriefInfo = searchServiceClient.getThemeBriefInfo(reservation.getThemeId());

        return ReservationDetailResDto.from(reservation, themeBriefInfo);
    }

    private void sendSuccessNotification(String memberId, ReservationReqDto reservationReqDto,
                                         Reservation reservation) {
        String fcmToken = memberServiceClient.getFcmToken(memberId).getFcmToken();
        ThemeBriefInfoInDto themeBriefInfo =
                searchServiceClient.getThemeBriefInfo(reservationReqDto.getThemeId());
        NotificationOutDto notificationOutDto =
                NotificationOutDto.success(memberId, fcmToken, themeBriefInfo.getTitle(), reservation);
        notificationServiceClient.notifyReservationSuccess(notificationOutDto);
    }

    private boolean reserveMasterkey(ReservationReqDto reservationReqDto) {

        ThemeOriginalInfoInDto originalInfo =
                searchServiceClient.getOriginalInfo(reservationReqDto.getThemeId());
        ReservingServerRequestDto reservingServerRequestDto = ReservingServerRequestDto.from(originalInfo,
                                                                                             reservationReqDto);
        return reservationAction.reserve(reservingServerRequestDto);
    }

    private void validateBookerInfo(String memberId, ReservationReqDto reservationReqDto) {
        MemberBriefInfoInDto memberBriefInfo = memberServiceClient.getMemberBriefInfo(memberId);
        if (!reservationReqDto.getBookerName().equals(memberBriefInfo.getBookerName()) || !reservationReqDto.getBookerPhoneNumber().equals(memberBriefInfo.getBookerPhoneNumber())) {
            throw BookerInfoInvalidException.EXCEPTION;
        }
    }
}
