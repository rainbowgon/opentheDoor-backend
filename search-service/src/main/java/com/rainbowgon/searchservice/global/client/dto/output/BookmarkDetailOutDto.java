package com.rainbowgon.searchservice.global.client.dto.output;

import com.rainbowgon.searchservice.domain.theme.model.Theme;
import com.rainbowgon.searchservice.domain.theme.model.entry.PriceEntry;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BookmarkDetailOutDto {

    private String themeId; // 테마 ID
    private String poster; // 테마 포스터
    private String title; // 테마명
    private String venue; // 지점명
    private String location; // 지점 위치
    private String explanation; // 설명
    private String venueTos; // 예약 사항
    private String tel; // 전화 번호
    private String[] genre; // 장르
    private Integer maxHeadcount; // 최대 인원 수
    private Integer minHeadcount; // 최소 인원 수
    private List<PriceEntry> priceList; // 가격
    private Integer timeLimit; // 소요 시간 (분 단위)
    private Double level; // 난이도
    private Double activity; // 활동성
    private Double lockRatio; // 장치 비율
    private Double horror; // 공포도
    private Double latitude; // 위도
    private Double longitude; // 경도

    public static BookmarkDetailOutDto from(Theme theme) {
        return BookmarkDetailOutDto.builder()
                .themeId(theme.getThemeId())
                .poster(theme.getPoster())
                .title(theme.getTitle())
                .venue(theme.getVenue())
                .location(theme.getLocation())
                .explanation(theme.getExplanation())
                .venueTos(theme.getVenueToS())
                .tel(theme.getTel())
                .genre(theme.getGenre())
                .minHeadcount(theme.getMinHeadcount())
                .maxHeadcount(theme.getMaxHeadcount())
                .priceList(theme.getPriceList())
                .timeLimit(theme.getTimeLimit())
                .level(theme.getLevel())
                .activity(theme.getActivity())
                .lockRatio(theme.getLockRatio())
                .horror(theme.getHorror())
                .latitude(theme.getLatitude())
                .longitude(theme.getLongitude())
                .build();
    }
}
