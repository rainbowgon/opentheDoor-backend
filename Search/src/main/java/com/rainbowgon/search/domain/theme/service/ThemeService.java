package com.rainbowgon.search.domain.theme.service;

import com.rainbowgon.search.domain.theme.dto.response.ThemeDetailResDto;
import com.rainbowgon.search.domain.theme.dto.response.ThemeSimpleResDto;
import com.rainbowgon.search.domain.theme.model.Theme;

import java.util.List;

public interface ThemeService {

    List<ThemeSimpleResDto> searchThemes(String keyword, Integer page, Integer size);

//    List<ThemeSimpleResDto> searchThemes(String keyword);

//    Page<Theme> search(String keyword, Pageable pageable);

    List<Theme> search(String keyword);

    ThemeDetailResDto selectOneThemeById(String themeId);

//    Page<Theme> sort(Page<Theme> pagedTheme, String sortBy);

    public void reviewCnt(String themeId);

    public void bookmarkCnt(String themeId);


}
