package com.rainbowgon.memberservice.domain.bookmark.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BookmarkSimpleResDto { // 홈 화면에서 보여지는 북마크 리스트 객체

    private Long themeId; // 테마 ID
    private String poster; // 테마 포스터
    private String title; // 테마명
    private String venue; // 지점명
    private Float rating; // 평균 별점
    private Integer reviewCnt; // 리뷰 수(별점 수)

    public static BookmarkSimpleResDto of() { // 테마 정보, 리뷰 정보
        return BookmarkSimpleResDto.builder()
                .build();
    }
}
