package com.rainbowgon.memberservice.global.client.dto.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeSimpleInDto {

    private String themeId; // 테마 ID
    private String poster; // 테마 포스터
    private String title; // 테마명
    private String venue; // 지점명
    private Double ratingScore; // 평균 별점
    private Integer reviewCnt; // 리뷰 수(별점 수)
}
