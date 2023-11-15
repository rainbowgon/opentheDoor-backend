package com.rainbowgon.searchservice.global.client.dto.output;

import com.rainbowgon.searchservice.domain.theme.model.Theme;
import lombok.*;

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
    private String[] genre; // 장르
    private Integer maxHeadcount; // 최대 인원 수
    private Integer minHeadcount; // 최소 인원 수
    private Integer timeLimit; // 소요 시간 (분 단위)
    private Double level; // 난이도
    private Double activity; // 활동성
    private Double lockRatio; // 장치 비율
    private Double horror; // 공포도
    private Double ratingScore; // 평균 별점
    private Integer reviewCount; // 리뷰 수
    private Integer bookmarkCount; // 북마크 수


    public static BookmarkDetailOutDto from(Theme theme, Integer bookmarkCount, Integer reviewCount,
                                            Double ratingScore) {
        return BookmarkDetailOutDto.builder()
                .themeId(theme.getThemeId())
                .poster(theme.getPoster())
                .title(theme.getTitle())
                .venue(theme.getVenue())
                .location(theme.getLocation())
                .genre(theme.getGenre())
                .minHeadcount(theme.getMinHeadcount())
                .maxHeadcount(theme.getMaxHeadcount())
                .timeLimit(theme.getTimeLimit())
                .level(theme.getLevel())
                .activity(theme.getActivity())
                .lockRatio(theme.getLockRatio())
                .horror(theme.getHorror())
                .bookmarkCount(bookmarkCount)
                .reviewCount(reviewCount)
                .ratingScore(ratingScore)
                .build();
    }
}
