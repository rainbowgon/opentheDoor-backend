package com.rainbowgon.search.domain.theme.controller;

import com.rainbowgon.search.domain.theme.dto.response.ThemeDetailResDto;
import com.rainbowgon.search.domain.theme.dto.response.ThemeSimpleResDto;
import com.rainbowgon.search.domain.theme.service.ThemeService;
import com.rainbowgon.search.global.response.JsonResponse;
import com.rainbowgon.search.global.response.PageInfo;
import com.rainbowgon.search.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping("/searches")
    public ResponseEntity<ResponseWrapper<List<ThemeSimpleResDto>>> searchThemes(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        List<ThemeSimpleResDto> searchList = themeService.searchThemes(keyword, page, size);

        Integer totalElements = searchList.size();
        PageInfo pageInfo = new PageInfo(page, size, totalElements, totalElements / size);

        return JsonResponse.ok("성공적으로 검색이 완료되었습니다.", searchList, pageInfo);
    }

    @GetMapping("/{theme-id}")
    public ResponseEntity<ResponseWrapper<ThemeDetailResDto>> searchThemes(
            @PathVariable("theme-id") String themeId) {
        ThemeDetailResDto searchTheme = themeService.selectOneThemeById(themeId);

        return JsonResponse.ok("성공적으로 검색이 완료되었습니다.", searchTheme);
    }

    @GetMapping("/review/{theme-id}")
    public ResponseEntity<?> review(@PathVariable("theme-id") String themeId) {
        themeService.reviewCnt(themeId);

        return JsonResponse.ok("리뷰 완료되었습니다.");
    }

    @GetMapping("/bookmark/{theme-id}")
    public ResponseEntity<?> bookmark(@PathVariable("theme-id") String themeId) {
        themeService.bookmarkCnt(themeId);

        return JsonResponse.ok("북마크 완료되었습니다.");
    }

}
