package com.rainbowgon.search.domain.theme.service;

import com.rainbowgon.search.domain.theme.dto.response.ThemeDetailResDto;
import com.rainbowgon.search.domain.theme.dto.response.ThemeSimpleResDto;
import com.rainbowgon.search.domain.theme.model.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ThemeService {

    List<ThemeSimpleResDto> searchThemes(String keyword, Integer page, Integer size);

    Page<Theme> search(String keyword, Pageable pageable);

    ThemeDetailResDto selectOneThemeById(String themeId);

    public void reviewCnt(String themeId);

    public void bookmarkCnt(String themeId);


}
