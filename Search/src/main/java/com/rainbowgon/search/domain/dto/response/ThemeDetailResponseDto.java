package com.rainbowgon.search.domain.dto.response;

import com.rainbowgon.search.domain.model.Theme;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ThemeDetailResponseDto {

    private String id;
    private String venue; // 지점명
    private String title; // 타이틀
    private String explanation; // 설명
    private String img; // 포스터
    private String[] genre; // 장르
    private Integer level; // 난이도
    private Integer minHeadcount; // 최소 인원 수
    private Integer maxHeadcount; // 최대 인원 수

//    private Integer price; // 가격
//    private Integer runningTime; // 소요 시간

    public static ThemeDetailResponseDto from(Theme theme) {
        return ThemeDetailResponseDto.builder()
                .id(theme.getId())
                .venue(theme.getVenue())
                .title(theme.getTitle())
                .explanation(theme.getExplanation())
                .img(theme.getImg())
                .genre(theme.getGenre())
                .level(theme.getLevel())
                .minHeadcount(theme.getMinHeadcount())
                .maxHeadcount(theme.getMaxHeadcount())
                .build();
    }
}
