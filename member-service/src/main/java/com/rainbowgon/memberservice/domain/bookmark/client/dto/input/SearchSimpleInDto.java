package com.rainbowgon.memberservice.domain.bookmark.client.dto.input;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SearchSimpleInDto {

    private String themeId; // 테마 ID
    private String title; // 테마명
    private String venue; // 지점명
}
