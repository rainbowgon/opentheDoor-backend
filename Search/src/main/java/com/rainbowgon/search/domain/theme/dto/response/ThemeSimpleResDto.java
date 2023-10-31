package com.rainbowgon.search.domain.theme.dto.response;


import com.rainbowgon.search.domain.theme.model.Theme;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ThemeSimpleResDto {

    private String id;
    private String venue; // 지점명
    private String title; // 테마명
    private String img; // 포스터
    //    private String explanation; // 테마 설명
    private Integer level; // 난이도
    private Integer minHeadcount; // 최소 인원 수
    private Integer maxHeadcount; // 최대 인원 수

//    private Integer price; // 가격
//    private Integer runningTime; // 소요 시간

    public static ThemeSimpleResDto from(Theme theme) {
        return ThemeSimpleResDto.builder()
                .id(theme.getId())
                .venue(theme.getVenue())
                .title(theme.getTitle())
                .img(theme.getImg())
                .level(theme.getLevel())
                .minHeadcount(theme.getMinHeadcount())
                .maxHeadcount(theme.getMaxHeadcount())
                .build();
    }

}
