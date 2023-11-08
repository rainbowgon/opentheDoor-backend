package com.rainbowgon.searchservice.domain.theme.dto.request;

import com.rainbowgon.searchservice.domain.theme.model.Theme;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeCreateReqDto {

    private String id; // 테마 ID
    private String poster; // 테마 포스터
    private String title; // 테마명
    private String venue; // 지점명
    private String location; // 지점 위치
    private String explanation; // 설명
    private String reservationNotice; // 예약 사항
    private String tel;// 전화번호
    private String[] genre; // 장르
    private Integer maxHeadcount; // 최대 인원 수
    private Integer minHeadcount; // 최소 인원 수
    private Integer price; // 가격
    private Integer timeLimit; // 소요 시간 (분 단위)
    private Double level; // 난이도
    private Double activity; // 활동성
    private Double lockRatio; // 장치 비율
    private Double horror; // 공포도

    public Theme toDocument(ThemeCreateReqDto syncTheme) {
        return Theme.builder()
                .venue(syncTheme.getVenue())
                .title(syncTheme.getTitle())
                .explanation(syncTheme.getExplanation())
                .location(syncTheme.getLocation())
                .poster(syncTheme.getPoster())
                .genre(syncTheme.getGenre())
                .level(syncTheme.getLevel())
                .minHeadcount(syncTheme.getMinHeadcount())
                .maxHeadcount(syncTheme.getMaxHeadcount())
                .timeLimit(syncTheme.getTimeLimit())
                .activity(syncTheme.getActivity())
                .lockRatio(syncTheme.getLockRatio())
                .horror(syncTheme.getHorror())
                .price(syncTheme.getPrice())
                .tel(syncTheme.getTel())
                .reservationNotice(syncTheme.getReservationNotice())
                .build();
    }
}