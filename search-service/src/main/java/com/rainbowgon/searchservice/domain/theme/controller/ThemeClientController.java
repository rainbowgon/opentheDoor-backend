package com.rainbowgon.searchservice.domain.theme.controller;

import com.rainbowgon.searchservice.domain.theme.dto.request.ThemeCheckReqDtoList;
import com.rainbowgon.searchservice.domain.theme.service.ThemeService;
import com.rainbowgon.searchservice.global.client.dto.output.BookmarkDetailOutDto;
import com.rainbowgon.searchservice.global.client.dto.output.BookmarkSimpleOutDto;
import com.rainbowgon.searchservice.global.response.JsonResponse;
import com.rainbowgon.searchservice.global.response.ResponseWrapper;
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
    public ResponseEntity<ResponseWrapper<List<BookmarkDetailOutDto>>> selectDetailThemes(
            @RequestBody ThemeCheckReqDtoList themeCheckReqDtoList) {
        List<BookmarkDetailOutDto> BookmarkDetailOutDtoList =
                themeService.selectDetailThemesById(themeCheckReqDtoList);

        return JsonResponse.ok("성공적으로 조회(디테일리스트)가 완료되었습니다.", BookmarkDetailOutDtoList);
    }

    @GetMapping("/simple-lists")
    public ResponseEntity<ResponseWrapper<List<BookmarkSimpleOutDto>>> selectSimpleThemes(
            @RequestBody ThemeCheckReqDtoList themeCheckReqDtoList) {
        List<BookmarkSimpleOutDto> BookmarkSimpleOutDtoList =
                themeService.selectSimpleThemesById(themeCheckReqDtoList);

        return JsonResponse.ok("성공적으로 조회(심플리스트)가 완료되었습니다.", BookmarkSimpleOutDtoList);
    }
}