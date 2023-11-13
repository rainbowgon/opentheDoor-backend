package com.rainbowgon.searchservice.domain.theme.dto.response;

import com.rainbowgon.searchservice.domain.theme.model.Theme;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ThemeDetailResDto {

    /* 테마 정보 */
    private String id; // 테마 ID
    private String poster; // 테마 포스터
    private String title; // 테마명
    private String venue; // 지점명
    private String location; // 지점 위치
    private String explanation; // 설명
    private String reservationNotice; // 예약 사항
    private String tel; // 전화 번호
    private String[] genre; // 장르
    private Integer maxHeadcount; // 최대 인원 수
    private Integer minHeadcount; // 최소 인원 수
    private Integer price; // 가격
    private Integer timeLimit; // 소요 시간 (분 단위)
    private Double level; // 난이도
    private Double activity; // 활동성
    private Double lockRatio; // 장치 비율
    private Double horror; // 공포도
    private Double latitude; // 위도
    private Double longitude; // 경도


    public static ThemeDetailResDto from(Theme theme) {
        return ThemeDetailResDto.builder()
                .id(theme.getId())
                .venue(theme.getVenue())
                .title(theme.getTitle())
                .explanation(theme.getExplanation())
                .location(theme.getLocation())
                .tel(theme.getTel())
                .poster(theme.getPoster())
                .genre(theme.getGenre())
                .level(theme.getLevel())
                .minHeadcount(theme.getMinHeadcount())
                .maxHeadcount(theme.getMaxHeadcount())
                .reservationNotice(theme.getReservationNotice())
                .latitude(theme.getLatitude())
                .longitude(theme.getLongitude())
                .build();
    }
}
