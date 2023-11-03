package com.rainbowgon.memberservice.domain.bookmark.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BookmarkDetailResDto { // 마이페이지에서 보여지는 북마크 리스트 객체

    /* 테마 정보 */
    private Long themeId; // 테마 ID
    private String poster; // 테마 포스터
    private String title; // 테마명
    private String venue; // 지점명
    private String[] genre; // 장르
    private Integer maxHeadCnt; // 최대 인원 수
    private Integer minHeadCnt; // 최소 인원 수
    private Integer price; // 가격
    private Integer time; // 소요 시간 (분 단위)
    private Float level; // 난이도
    private Float activity; // 활동성
    private Float lockRatio; // 장치 비율
    private Float horrorLevel; // 공포도

    /* 리뷰 정보 */
    private Integer reviewCnt; // 리뷰 수(별점 수)
    private Float rating; // 평균 별점

    /* 북마크 정보 */
    private Long bookmarkId; // 북마크 ID
    private Integer bookmarkCnt; // 북마크 수


    public static BookmarkDetailResDto of() { // 테마 정보, 리뷰 정보, 북마크 정보
        return BookmarkDetailResDto.builder()
                .build();
    }

}
