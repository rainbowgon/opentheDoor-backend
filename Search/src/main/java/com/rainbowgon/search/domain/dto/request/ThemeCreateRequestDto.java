package com.rainbowgon.search.domain.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeCreateRequestDto {

    private String venue; // 지점명
    private String title; // 타이틀
    private String explanation; // 설명
    private String img; // 포스터
    private String[] genre; // 장르
    private Integer level; // 난이도
    private Integer minHeadcount; // 최소 인원 수
    private Integer maxHeadcount; // 최대 인원 수

}