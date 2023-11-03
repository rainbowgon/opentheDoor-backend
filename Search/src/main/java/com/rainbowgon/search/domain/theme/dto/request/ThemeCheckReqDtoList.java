package com.rainbowgon.search.domain.theme.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeCheckReqDtoList {

    private List<String> themeList;
}
