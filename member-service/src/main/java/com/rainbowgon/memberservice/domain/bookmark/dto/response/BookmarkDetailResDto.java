package com.rainbowgon.memberservice.domain.bookmark.dto.response;

import com.rainbowgon.memberservice.global.client.dto.input.ThemeDetailInDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BookmarkDetailResDto { // 마이페이지에서 보여지는 북마크 리스트 객체

    /* 테마 정보 */
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

    /* 리뷰 정보 */
    private Integer reviewCount; // 리뷰 수(별점 수)
    private Double ratingScore; // 평균 별점

    /* 북마크 정보 */
    private Integer bookmarkCount; // 북마크 수

    public static BookmarkDetailResDto from(ThemeDetailInDto theme) { // 테마 정보, 리뷰 정보, 북마크 정보
        return BookmarkDetailResDto.builder()
                .themeId(theme.getThemeId())
                .poster(theme.getPoster())
                .title(theme.getTitle())
                .venue(theme.getVenue())
                .location(theme.getLocation())
                .genre(theme.getGenre())
                .maxHeadcount(theme.getMaxHeadcount())
                .minHeadcount(theme.getMinHeadcount())
                .timeLimit(theme.getTimeLimit())
                .level(theme.getLevel())
                .activity(theme.getActivity())
                .lockRatio(theme.getLockRatio())
                .horror(theme.getHorror())
                .reviewCount(theme.getReviewCount())
                .ratingScore(theme.getRatingScore())
                .bookmarkCount(theme.getBookmarkCount())
                .build();
    }

}
