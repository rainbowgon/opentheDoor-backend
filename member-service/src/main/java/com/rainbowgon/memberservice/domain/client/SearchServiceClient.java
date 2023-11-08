package com.rainbowgon.memberservice.domain.client;

import com.rainbowgon.memberservice.domain.client.dto.input.SearchDetailInDto;
import com.rainbowgon.memberservice.domain.client.dto.input.SearchSimpleInDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(name = "search-service")
public interface SearchServiceClient {

    @GetMapping("/clients/themes/info")
    List<SearchDetailInDto> getBookmarkThemeDetailInfo(@RequestBody List<String> themeIdList);

    @GetMapping("/clients/themes/")
    List<SearchSimpleInDto> getBookmarkThemeSimpleInfo(@RequestBody Set<String> themeIdSet);
}
