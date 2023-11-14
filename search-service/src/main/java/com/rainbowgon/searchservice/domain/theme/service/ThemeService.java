package com.rainbowgon.searchservice.domain.theme.service;

import com.rainbowgon.searchservice.domain.theme.dto.response.ThemeDetailResDto;
import com.rainbowgon.searchservice.domain.theme.dto.response.ThemeSimpleResDto;
import com.rainbowgon.searchservice.domain.theme.model.Theme;
import com.rainbowgon.searchservice.global.client.dto.input.BookmarkInDtoList;
import com.rainbowgon.searchservice.global.client.dto.output.BookmarkDetailOutDto;
import com.rainbowgon.searchservice.global.client.dto.output.BookmarkSimpleOutDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ThemeService {

    Page<ThemeSimpleResDto> searchThemes(String keyword, Double latitude, Double longitude,
                                         Integer headcount, List<String> region, Integer page, Integer size);

    List<Theme> search(String keyword);

    ThemeDetailResDto selectOneThemeById(String themeId);

    List<BookmarkDetailOutDto> selectDetailThemesById(BookmarkInDtoList themeIdList);

    List<BookmarkSimpleOutDto> selectSimpleThemesById(BookmarkInDtoList themeIdList);

    Page<ThemeSimpleResDto> sort(String keyword, String sortBy, Double latitude, Double longitude,
                                 Integer headcount, List<String> region,
                                 Integer page, Integer size);

    void setRanks();

    List<ThemeSimpleResDto> getRanks();

    Integer getPrice(String themeId, Integer headcount);
}
