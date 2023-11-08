package com.rainbowgon.searchservice.domain.theme.dto.response;


import com.rainbowgon.searchservice.domain.theme.model.Theme;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ThemeSimpleResDto {

    private String id;
    private String venue; // 지점명
    private String title; // 테마명
    private String poster; // 포스터
    private Double level; // 난이도
    private Integer minHeadcount; // 최소 인원 수
    private Integer maxHeadcount; // 최대 인원 수
    private Integer price; // 가격
    private Integer timeLimit; // 소요 시간 (분 단위)
    private Double latitude; // 위도
    private Double longitude; // 경도

    public static ThemeSimpleResDto from(Theme theme) {
        return ThemeSimpleResDto.builder()
                .id(theme.getId())
                .venue(theme.getVenue())
                .title(theme.getTitle())
                .poster(theme.getPoster())
                .level(theme.getLevel())
                .minHeadcount(theme.getMinHeadcount())
                .maxHeadcount(theme.getMaxHeadcount())
                .latitude(theme.getLatitude())
                .longitude(theme.getLongitude())
                .build();
    }

}
