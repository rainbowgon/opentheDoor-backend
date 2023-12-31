package com.rainbowgon.memberservice.global.client.dto.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeDetailInDto {

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
}
