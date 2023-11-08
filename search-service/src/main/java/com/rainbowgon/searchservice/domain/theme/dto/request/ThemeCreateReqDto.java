package com.rainbowgon.searchservice.domain.theme.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeCreateReqDto {

    private String themeId; // 테마아이디
    private String venue; // 지점명
    private String title; // 타이틀
    private String explanation; // 설명
    private String poster; // 포스터
    private String[] genre; // 장르
    private Double level; // 난이도
    private Integer minHeadcount; // 최소 인원 수
    private Integer maxHeadcount; // 최대 인원 수

}