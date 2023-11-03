package com.rainbowgon.search.domain.theme.service;

import com.rainbowgon.search.domain.theme.dto.request.ThemeCheckReqDtoList;
import com.rainbowgon.search.domain.theme.dto.response.ThemeDetailResDto;
import com.rainbowgon.search.domain.theme.dto.response.ThemeSimpleResDto;
import com.rainbowgon.search.domain.theme.model.Theme;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ThemeService {

    Page<ThemeSimpleResDto> searchThemes(String keyword, Integer page, Integer size);

    List<Theme> search(String keyword);

    ThemeDetailResDto selectOneThemeById(String themeId);

    List<ThemeDetailResDto> selectThemeById(ThemeCheckReqDtoList themeIdList);

    Page<ThemeSimpleResDto> sort(String keyword, String sortBy, Integer page, Integer size);

    public void reviewCnt(String themeId);

    public void bookmarkCnt(String themeId);

    public void recommendCnt(String themeId);

}
