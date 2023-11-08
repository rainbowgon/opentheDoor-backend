package com.rainbowgon.searchservice.domain.theme.service;

import com.rainbowgon.searchservice.domain.theme.dto.request.ThemeCheckReqDtoList;
import com.rainbowgon.searchservice.domain.theme.dto.response.ThemeDetailResDto;
import com.rainbowgon.searchservice.domain.theme.dto.response.ThemeSimpleResDto;
import com.rainbowgon.searchservice.domain.theme.model.Theme;
import com.rainbowgon.searchservice.domain.theme.repository.ThemeRepository;
import com.rainbowgon.searchservice.global.error.exception.ThemeNotFoundException;
import com.rainbowgon.searchservice.global.utils.RedisKeyBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
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


    @Qualifier("sortingRedisDoubleTemplate")
    private final RedisTemplate<String, Double> sortingRedisDoubleTemplate;

    @Qualifier("sortingRedisStringTemplate")
    private final RedisTemplate<String, String> sortingRedisStringTemplate;


//    @Qualifier("doubleRedisTemplate")
//    private RedisTemplate<String, String> reservationRedisStringTemplate;

    @Qualifier("cacheRedisThemeTemplate")
    private final RedisTemplate<String, Theme> cacheRedisThemeTemplate;


    @Override
    @Transactional(readOnly = true)
    public Page<ThemeSimpleResDto> searchThemes(String keyword, Integer page, Integer size) {

        List<Theme> themeList = search(keyword);

        // 레디스에 저장할 키를 생성
        String sortingKey = RedisKeyBuilder.buildKey("BOOKMARK", keyword);
        String reviewKey = RedisKeyBuilder.buildKey("REVIEW", keyword);
        String recommendKey = RedisKeyBuilder.buildKey("RECOMMEND", keyword);

        // 여기서 기존의 점수가 있는지 체크하고, 없으면 0으로 초기화(기본 북마크)
        for (Theme theme : themeList) {
            //검색 결과로 나온 테마의 북마크 수
            Double bookmarkScore = Optional.ofNullable(sortingRedisStringTemplate.opsForZSet().score(
                    "BOOKMARK",
                    theme.getId())).orElse(0.0);
            //검색 결과로 나온 테마의 리뷰 수
            Double reviewScore = Optional.ofNullable(sortingRedisStringTemplate.opsForZSet().score(
                    "REVIEW",
                    theme.getId())).orElse(0.0);
            //검색 결과로 나온 테마의 평균 별점
            Double ratingScore = Optional.ofNullable(sortingRedisStringTemplate.opsForZSet().score(
                    "RATING",
                    theme.getId())).orElse(0.0);

            //검색 결과로 나온 테마의 조회 수
            Double viewScore =
                    Optional.ofNullable(sortingRedisDoubleTemplate.opsForValue().get(theme.getId())).orElse(0.0);

            Double interest = 0.4 * reviewScore + 0.3 * viewScore + 0.3 * bookmarkScore;

            Double finalRatingScore = ratingScore - (ratingScore - 0.5) * 2 - Math.log(interest);

            cacheRedisThemeTemplate.opsForZSet().add(sortingKey, theme, bookmarkScore);
            cacheRedisThemeTemplate.expire(sortingKey, Duration.ofHours(2));
            cacheRedisThemeTemplate.opsForZSet().add(reviewKey, theme, reviewScore);
            cacheRedisThemeTemplate.expire(reviewKey, Duration.ofHours(2));
            cacheRedisThemeTemplate.opsForZSet().add(recommendKey, theme, finalRatingScore);
            cacheRedisThemeTemplate.expire(recommendKey, Duration.ofHours(2));
        }

        // 페이지네이션을 위한 시작과 끝 인덱스 계산
        long start = page * size;
        long end = (page + 1) * size - 1;

        // 레디스에서 전체 zset의 크기를 가져옵니다.
        Long totalElements = cacheRedisThemeTemplate.opsForZSet().zCard(sortingKey);

        // 레디스에서 정렬된 결과를 가져옵니다.
        Set<Theme> sortedThemeIds = cacheRedisThemeTemplate.opsForZSet().reverseRange(sortingKey, start,
                                                                                      end);

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

        if (keyword != null) {
            themeList = themeRepository.searchByKeyword(keyword);

        } else if (keyword == null) {
            Iterable<Theme> themes = themeRepository.findAll();
            themeList = StreamSupport.stream(themes.spliterator(), false) // ElasticsearchRepository는
                    // findAll이 List가 아닌 Iterable가 나와서 변환 필요
                    .collect(Collectors.toList());
        }

        return themeList;
    }

    @Override
    @Transactional(readOnly = true)
    public ThemeDetailResDto selectOneThemeById(String themeId) {
        ValueOperations<String, Double> valueOperations = sortingRedisDoubleTemplate.opsForValue();
        Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        valueOperations.increment(themeId, 1);

        return ThemeDetailResDto.from(theme);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThemeDetailResDto> selectThemesById(ThemeCheckReqDtoList themeIdList) {
        List<ThemeDetailResDto> themeDetailResDtoList = new ArrayList<>();
        for (String themeId : themeIdList.getThemeList()) {
            Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
            themeDetailResDtoList.add(ThemeDetailResDto.from(theme));
        }
        return themeDetailResDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemeSimpleResDto> sort(String keyword, String sortBy, Integer page, Integer size) {

        String redisKey = RedisKeyBuilder.buildKey(sortBy, keyword);

        long start = page * size; // 페이지 계산에 따른 시작 인덱스
        long end = (page + 1) * size - 1; // 페이지 계산에 따른 끝 인덱스

        // 레디스에서 정렬된 결과를 가져와서 DTO로 변환
        Set<Theme> sortedThemeIds = cacheRedisThemeTemplate.opsForZSet().reverseRange(redisKey, start, end);

        Long totalElements = cacheRedisThemeTemplate.opsForZSet().zCard(redisKey);

        List<ThemeSimpleResDto> content = sortedThemeIds.stream()
                .map(ThemeSimpleResDto::from)
                .collect(Collectors.toList());

        return new PageImpl<>(content, PageRequest.of(page, size), totalElements);
    }


    public void bookmarkCnt(String themeId) {
        ZSetOperations<String, String> zSetOperations = sortingRedisStringTemplate.opsForZSet();

        Boolean themeExists = zSetOperations.score("BOOKMARK", themeId) != null;

        if (themeExists) {
            // If the member exists, increment its score by 1
            zSetOperations.incrementScore("BOOKMARK", themeId, 1);

        } else {
            // If the member does not exist, add it to the ZSET with a score of 1
            zSetOperations.add("BOOKMARK", themeId, 1);
        }

    }

    public void reviewCnt(String themeId) {
        ZSetOperations<String, String> zSetOperations = sortingRedisStringTemplate.opsForZSet();
        Boolean themeExists = zSetOperations.score("REVIEW", themeId) != null;

        if (themeExists) {
            // If the member exists, increment its score by 1
            zSetOperations.incrementScore("REVIEW", themeId, 1);
        } else {
            // If the member does not exist, add it to the ZSET with a score of 1
            zSetOperations.add("REVIEW", themeId, 1);
        }
    }

    public void recommendCnt(String themeId) {
        ZSetOperations<String, String> zSetOperations = sortingRedisStringTemplate.opsForZSet();
        Boolean themeExists = zSetOperations.score("RECOMMEND", themeId) != null;

        if (themeExists) {
            // If the member exists, increment its score by 1
            zSetOperations.incrementScore("RECOMMEND", themeId, 1);
        } else {
            // If the member does not exist, add it to the ZSET with a score of 1
            zSetOperations.add("RECOMMEND", themeId, 1);
        }
    }

}
