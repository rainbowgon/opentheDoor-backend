package com.rainbowgon.memberservice.domain.bookmark.client;

import com.rainbowgon.memberservice.domain.bookmark.client.dto.input.SearchThemeInDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "search-service")
public interface SearchServiceClient {

    @GetMapping("/themes/info")
    List<SearchThemeInDto> getBookmarkThemeInfo(@RequestBody List<String> themeIdList);
}
