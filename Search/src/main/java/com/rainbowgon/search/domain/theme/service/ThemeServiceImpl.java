package com.rainbowgon.search.domain.theme.service;

import com.rainbowgon.search.domain.theme.dto.response.ThemeDetailResDto;
import com.rainbowgon.search.domain.theme.dto.response.ThemeSimpleResDto;
import com.rainbowgon.search.domain.theme.model.Theme;
import com.rainbowgon.search.domain.theme.repository.ThemeRepository;
import com.rainbowgon.search.global.error.exception.ThemeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, Theme> themeRedisTemplate;


//    @Override
//    @Transactional(readOnly = true)
//    public List<ThemeSimpleResDto> searchThemes(String keyword, Integer page, Integer size) {
//
//        Page<Theme> pageTheme = search(keyword, PageRequest.of(page, size));
//
//        return pageTheme.stream()
//                .map(ThemeSimpleResDto::from)
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional(readOnly = true)
    public List<ThemeSimpleResDto> searchThemes(String keyword, Integer page, Integer size) {
        List<Theme> themeList = search(keyword);
        System.out.println("themeList = " + themeList);
        // 레디스에 저장할 키를 생성
        String redisKey = keyword + ":bookmark";
        System.out.println("redisKey = " + redisKey);
        // themeList의 themeId와 BOOKMARK에 저장된 score를 가져와서 zset에 저장
        for (Theme theme : themeList) {
            System.out.println("theme.getId() = " + theme.getId());
            System.out.println("theme.getThemeId() = " + theme.getThemeId());
            Double score = redisTemplate.opsForZSet().score("BOOKMARK", theme.getThemeId());
            System.out.println("score = " + score);
            if (score != null) {
                themeRedisTemplate.opsForZSet().add(redisKey, theme, score);
            }
        }

        // 레디스에서 정렬된 결과를 가져와서 DTO로 변환
        Set<Theme> sortedThemeIds = themeRedisTemplate.opsForZSet().reverseRange(redisKey, page * 10, page * 10 + size);

        return sortedThemeIds.stream()
                .map(ThemeSimpleResDto::from)
                .collect(Collectors.toList());
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

//    @Override
//    @Transactional(readOnly = true)
//    public Page<Theme> sort(List<Theme> pagedTheme, String sortBy) {
//        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
//
//        List<Theme> sortedContent = pagedTheme.getContent().stream()
//                .sorted((theme1, theme2) -> {
//                    Double score1 = zSetOperations.score(sortBy, theme1.getId());
//                    Double score2 = zSetOperations.score(sortBy, theme2.getId());
//                    return score2.compareTo(score1);  // Descending order
//                })
//                .collect(Collectors.toList());
//
//        Page<Theme> sortedPage = new PageImpl<>(sortedContent, pagedTheme.getPageable(), pagedTheme.getTotalElements());
//
//        return sortedPage;
//    }


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

}


//    public void insertDocument(ThemeCreateReqDto themeCreateReqDto) {
//        final Theme theme = Theme.of(themeCreateReqDto);
//        themeRepository.save(theme);
//    }

//    public void updateDocument(String themeId, ThemeCreateRequestDto themeCreateRequestDto) {
//        final Theme oldTheme = themeRepository
//                .findThemeByThemesUuid(promptUuid)
//                .orElseThrow(ThemeNotFoundException::new);
//        themeRepository.delete(oldTheme);
//        final Theme newTheme = Theme.of(themeCreateRequestDto);
//        themeRepository.save(newTheme);
//    }

//    public void deleteDocument(String themeId) {
//        final Theme theme = themeRepository
//                .findThemeByThemeId(themeId).get();
////                .orElseThrow(ThemeNotFoundException::new);
//        themeRepository.delete(theme);
//    }
