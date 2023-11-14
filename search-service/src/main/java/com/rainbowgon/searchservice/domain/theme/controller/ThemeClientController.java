package com.rainbowgon.searchservice.domain.theme.controller;

import com.rainbowgon.searchservice.domain.theme.service.ThemeService;
import com.rainbowgon.searchservice.global.client.dto.input.BookmarkInDtoList;
import com.rainbowgon.searchservice.global.client.dto.output.BookmarkDetailOutDto;
import com.rainbowgon.searchservice.global.client.dto.output.BookmarkSimpleOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients/themes")
public class ThemeClientController {

    private final ThemeService themeService;

    @PostMapping("/detail-lists")
    public ResponseEntity<List<BookmarkDetailOutDto>> selectDetailThemes(
            @RequestBody BookmarkInDtoList bookmarkInDtoList) {
        List<BookmarkDetailOutDto> BookmarkDetailOutDtoList =
                themeService.selectDetailThemesById(bookmarkInDtoList);

        return ResponseEntity.ok(BookmarkDetailOutDtoList);
    }

    @PostMapping("/simple-lists")
    public ResponseEntity<List<BookmarkSimpleOutDto>> selectSimpleThemes(
            @RequestBody BookmarkInDtoList bookmarkInDtoList) {
        List<BookmarkSimpleOutDto> BookmarkSimpleOutDtoList =
                themeService.selectSimpleThemesById(bookmarkInDtoList);

        return ResponseEntity.ok(BookmarkSimpleOutDtoList);
    }

    @GetMapping("/{theme-id}/{headcount}")
    public ResponseEntity<Integer> selectSimpleThemes(
            @PathVariable("theme-id") String themeId,
            @PathVariable("headcount") Integer headcount
    ) {
        Integer price = themeService.getPrice(themeId, headcount);

        return ResponseEntity.ok(price);
    }
}