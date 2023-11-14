package com.rainbowgon.searchservice.domain.theme.controller;

import com.rainbowgon.searchservice.domain.theme.dto.request.ThemeCheckReqDtoList;
import com.rainbowgon.searchservice.domain.theme.service.ThemeService;
import com.rainbowgon.searchservice.global.client.dto.output.BookmarkDetailOutDto;
import com.rainbowgon.searchservice.global.client.dto.output.BookmarkSimpleOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("clients/themes")
public class ThemeClientController {

    private final ThemeService themeService;

    @GetMapping("/detail-lists")
    public ResponseEntity<List<BookmarkDetailOutDto>> selectDetailThemes(
            @RequestBody ThemeCheckReqDtoList themeCheckReqDtoList) {
        List<BookmarkDetailOutDto> BookmarkDetailOutDtoList =
                themeService.selectDetailThemesById(themeCheckReqDtoList);

        return ResponseEntity.ok(BookmarkDetailOutDtoList);
    }

    @GetMapping("/simple-lists")
    public ResponseEntity<List<BookmarkSimpleOutDto>> selectSimpleThemes(
            @RequestBody ThemeCheckReqDtoList themeCheckReqDtoList) {
        List<BookmarkSimpleOutDto> BookmarkSimpleOutDtoList =
                themeService.selectSimpleThemesById(themeCheckReqDtoList);

        return ResponseEntity.ok(BookmarkSimpleOutDtoList);
    }
}