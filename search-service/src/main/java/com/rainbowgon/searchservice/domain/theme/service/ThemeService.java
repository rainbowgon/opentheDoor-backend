package com.rainbowgon.searchservice.domain.theme.service;

import com.rainbowgon.searchservice.domain.theme.dto.request.ThemeCheckReqDtoList;
import com.rainbowgon.searchservice.domain.theme.dto.response.ThemeDetailResDto;
import com.rainbowgon.searchservice.domain.theme.dto.response.ThemeSimpleResDto;
import com.rainbowgon.searchservice.domain.theme.model.Theme;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ThemeService {

    Page<ThemeSimpleResDto> searchThemes(String keyword, Integer page, Integer size);

    List<Theme> search(String keyword);

    ThemeDetailResDto selectOneThemeById(String themeId);

    List<ThemeDetailResDto> selectThemesById(ThemeCheckReqDtoList themeIdList);

    Page<ThemeSimpleResDto> sort(String keyword, String sortBy, Integer page, Integer size);

    void reviewCnt(String themeId);

    void bookmarkCnt(String themeId);

    public void recommendCnt(String themeId);

}
