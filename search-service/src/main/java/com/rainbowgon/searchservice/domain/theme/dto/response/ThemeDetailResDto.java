package com.rainbowgon.searchservice.domain.theme.dto.response;

import com.rainbowgon.searchservice.domain.theme.model.Theme;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ThemeDetailResDto {

    private String id;
    private String venue; // 지점명
    private String title; // 타이틀
    private String explanation; // 설명
    private String poster; // 포스터
    private String[] genre; // 장르
    private Integer level; // 난이도
    private Integer minHeadcount; // 최소 인원 수
    private Integer maxHeadcount; // 최대 인원 수
    private Integer price; // 가격
//    private Integer runningTime; // 소요 시간

    public static ThemeDetailResDto from(Theme theme) {
        return ThemeDetailResDto.builder()
                .id(theme.getId())
                .venue(theme.getVenue())
                .title(theme.getTitle())
                .explanation(theme.getExplanation())
                .poster(theme.getPoster())
                .genre(theme.getGenre())
                .level(theme.getLevel())
                .minHeadcount(theme.getMinHeadcount())
                .maxHeadcount(theme.getMaxHeadcount())
                .build();
    }
}
