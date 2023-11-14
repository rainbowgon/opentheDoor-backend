package com.rainbowgon.searchservice.domain.theme.controller;

import com.rainbowgon.searchservice.domain.theme.service.ThemeService;
import com.rainbowgon.searchservice.global.client.dto.input.BookmarkInDtoList;
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
@RequestMapping("/clients/themes")
public class ThemeClientController {

    private final ThemeService themeService;

    @GetMapping("/detail-lists")
    public ResponseEntity<List<BookmarkDetailOutDto>> selectDetailThemes(
            @RequestBody BookmarkInDtoList bookmarkInDtoList) {
        List<BookmarkDetailOutDto> BookmarkDetailOutDtoList =
                themeService.selectDetailThemesById(bookmarkInDtoList);

        return ResponseEntity.ok(BookmarkDetailOutDtoList);
    }

    @GetMapping("/simple-lists")
    public ResponseEntity<List<BookmarkSimpleOutDto>> selectSimpleThemes(
            @RequestBody BookmarkInDtoList bookmarkInDtoList) {
        List<BookmarkSimpleOutDto> BookmarkSimpleOutDtoList =
                themeService.selectSimpleThemesById(bookmarkInDtoList);

        return ResponseEntity.ok(BookmarkSimpleOutDtoList);
    }
}