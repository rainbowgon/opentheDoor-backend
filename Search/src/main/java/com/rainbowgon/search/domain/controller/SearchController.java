package com.rainbowgon.search.domain.controller;

import com.rainbowgon.search.domain.dto.response.ThemeSimpleResponseDto;
import com.rainbowgon.search.domain.service.SearchService;
import com.rainbowgon.search.global.response.JsonResponse;
import com.rainbowgon.search.global.response.PageInfo;
import com.rainbowgon.search.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/searches")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/themes")
    public ResponseEntity<ResponseWrapper<List<ThemeSimpleResponseDto>>> searchThemes(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<ThemeSimpleResponseDto> searchList = searchService.searchThemes(keyword, page, size);

        Integer totalElements = searchList.size();
        PageInfo pageInfo = new PageInfo(page, size, totalElements, totalElements / size);

        return JsonResponse.ok("성공적으로 검색이 완료되었습니다.", searchList, pageInfo);
    }


}
