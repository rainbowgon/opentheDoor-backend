package com.rainbowgon.search.domain.theme.service;

import com.rainbowgon.search.domain.theme.dto.request.ThemeCheckReqDtoList;
import com.rainbowgon.search.domain.theme.dto.response.ThemeDetailResDto;
import com.rainbowgon.search.domain.theme.dto.response.ThemeSimpleResDto;
import com.rainbowgon.search.domain.theme.model.Theme;
import com.rainbowgon.search.domain.theme.repository.ThemeRepository;
import com.rainbowgon.search.global.error.exception.ThemeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, Theme> themeRedisTemplate;

    @Override
    @Transactional(readOnly = true)
    public Page<ThemeSimpleResDto> searchThemes(String keyword, Integer page, Integer size) {

        List<Theme> themeList = search(keyword);

        // 레디스에 저장할 키를 생성
        String bookmarkKey = keyword + ":BOOKMARK";
        String reviewKey = keyword + ":REVIEW";

        System.out.println(redisTemplate.opsForZSet().zCard(bookmarkKey));
        // 여기서 기존의 점수가 있는지 체크하고, 없으면 0으로 초기화(기본 북마크)
        if (redisTemplate.opsForZSet().zCard(bookmarkKey) == 0) {
            for (Theme theme : themeList) {
                Double bookmarkScore = Optional.ofNullable(redisTemplate.opsForZSet().score("BOOKMARK", theme.getId())).orElse(0.0);
                Double reviewScore = Optional.ofNullable(redisTemplate.opsForZSet().score("REVIEW", theme.getId())).orElse(0.0);

                themeRedisTemplate.opsForZSet().add(bookmarkKey, theme, bookmarkScore);
                themeRedisTemplate.opsForZSet().add(reviewKey, theme, reviewScore);
            }
        }

        // 페이지네이션을 위한 시작과 끝 인덱스 계산
        long start = page * size;
        long end = (page + 1) * size - 1;

        // 레디스에서 전체 zset의 크기를 가져옵니다.
        Long totalElements = themeRedisTemplate.opsForZSet().zCard(bookmarkKey);

        // 레디스에서 정렬된 결과를 가져옵니다.
        Set<Theme> sortedThemeIds = themeRedisTemplate.opsForZSet().reverseRange(bookmarkKey, start, end);

        // 결과를 DTO로 변환합니다.
        List<ThemeSimpleResDto> content = sortedThemeIds.stream()
                .map(ThemeSimpleResDto::from)
                .collect(Collectors.toList());

        // Page 객체를 생성하고 반환합니다.
        return new PageImpl<>(content, PageRequest.of(page, size), totalElements);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Theme> search(String keyword) {
        List<Theme> themeList = null;
        keyword = (keyword.equals("")) ? null : keyword;

        if (null != keyword) {
            themeList = themeRepository.searchByKeyword(keyword);

        } else if (null == keyword) {
            Iterable<Theme> themes = themeRepository.findAll();
            themeList = StreamSupport.stream(themes.spliterator(), false) // ElasticsearchRepository는 findAll이 List가 아닌 Iterable가 나와서 변환 필요
                    .collect(Collectors.toList());
        }

        return themeList;
    }

    @Override
    @Transactional(readOnly = true)
    public ThemeDetailResDto selectOneThemeById(String themeId) {
        Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);

        return ThemeDetailResDto.from(theme);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThemeDetailResDto> selectThemeById(ThemeCheckReqDtoList themeIdList) {
        List<ThemeDetailResDto> themeDetailResDtoList = new ArrayList<>();
        System.out.println(themeIdList.getThemeList());
        for (String themeId : themeIdList.getThemeList()) {
            Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
            themeDetailResDtoList.add(ThemeDetailResDto.from(theme));
        }
        System.out.println(themeDetailResDtoList.size());
        return themeDetailResDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemeSimpleResDto> sort(String keyword, String sortBy, Integer page, Integer size) {

        String redisKey = keyword + ":" + sortBy;

        long start = page * size; // 페이지 계산에 따른 시작 인덱스
        long end = (page + 1) * size - 1; // 페이지 계산에 따른 끝 인덱스

        // 레디스에서 정렬된 결과를 가져와서 DTO로 변환
        Set<Theme> sortedThemeIds = themeRedisTemplate.opsForZSet().reverseRange(redisKey, start, end);

        Long totalElements = themeRedisTemplate.opsForZSet().zCard(redisKey);

        List<ThemeSimpleResDto> content = sortedThemeIds.stream()
                .map(ThemeSimpleResDto::from)
                .collect(Collectors.toList());

        return new PageImpl<>(content, PageRequest.of(page, size), totalElements);
    }


    public void bookmarkCnt(String themeId) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

        Boolean themeExists = zSetOperations.score("BOOKMARK", themeId) != null;

        if (themeExists) {
            // If the member exists, increment its score by 1
            zSetOperations.incrementScore("BOOKMARK", themeId, 1);
            System.out.println("BOOKMARK = " + zSetOperations.score("BOOKMARK", themeId));

        } else {
            // If the member does not exist, add it to the ZSET with a score of 1
            zSetOperations.add("BOOKMARK", themeId, 1);
        }

    }

    public void reviewCnt(String themeId) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Boolean themeExists = zSetOperations.score("REVIEW", themeId) != null;

        if (themeExists) {
            // If the member exists, increment its score by 1
            zSetOperations.incrementScore("REVIEW", themeId, 1);
            System.out.println("REVIEW = " + zSetOperations.score("REVIEW", themeId));
        } else {
            // If the member does not exist, add it to the ZSET with a score of 1
            zSetOperations.add("REVIEW", themeId, 1);
        }
    }

    public void recommendCnt(String themeId) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Boolean themeExists = zSetOperations.score("RECOMMEND", themeId) != null;

        if (themeExists) {
            // If the member exists, increment its score by 1
            zSetOperations.incrementScore("RECOMMEND", themeId, 1);
            System.out.println("RECOMMEND = " + zSetOperations.score("RECOMMEND", themeId));
        } else {
            // If the member does not exist, add it to the ZSET with a score of 1
            zSetOperations.add("RECOMMEND", themeId, 1);
        }
    }

}
