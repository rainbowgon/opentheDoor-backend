package com.rainbowgon.search.domain.theme.service;

import com.rainbowgon.search.domain.theme.dto.response.ThemeDetailResDto;
import com.rainbowgon.search.domain.theme.dto.response.ThemeSimpleResDto;
import com.rainbowgon.search.domain.theme.model.Theme;
import com.rainbowgon.search.domain.theme.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final RedisTemplate<String, String> redisTemplate;
//    private final RedisTemplate<String, Integer> redisTemplate;


    @Override
    public List<ThemeSimpleResDto> searchThemes(String keyword, Integer page, Integer size) {

        Page<Theme> pageTheme = search(keyword, PageRequest.of(page, size));

//        List<ThemeSimpleResponseDto> themeList = );
//        final Page<Theme> pagedTheme = search(keyword, page, size);
//        final long totalThemessCnt = pagedTheme.getTotalElements();
//        final int totalPageCnt = pagedTheme.getTotalPages();
//
//        final List<SearchThemes> searchThemes = new ArrayList<>();
//        for (Theme themeCreateRequestDto : pagedTheme) {
//            // prompt 서버에서 필요한 정보 조회
//            final UUID promptUuid = UUID.fromString(themeCreateRequestDto.getThemesUuid());
//            final SearchFromThemesResponse fromThemes = circuitBreaker
//                    .run(() -> promptClient.searchFromThemes(promptUuid, crntMemberUuid));
//
//            // 사용자 조회
//            final MemberResponse member = circuitBreaker
//                    .run(() -> memberClient
//                            .getMemberInfo(UUID.fromString(themeCreateRequestDto.getUserId()))
//                            .orElseThrow(MemberNotFoundException::new));
//            final WriterResponse writer = member.toWriterResponse();
//
//            // dto로 변환하기
//            searchThemess.add(SearchThemes.of(
//                    themeCreateRequestDto,
//                    fromThemes,
//                    writer
//            ));
//
//        }
        return pageTheme.stream()
                .map(ThemeSimpleResDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Theme> search(String keyword, Pageable pageable) {
        Page<Theme> pagedTheme = null;
        keyword = (keyword.equals("")) ? null : keyword;

        if (null != keyword) {
            pagedTheme = themeRepository.searchByKeyword(keyword, pageable);

        } else if (null == keyword) {
            pagedTheme = themeRepository.findAll(pageable);
        }

        return pagedTheme;
    }

    @Override
    public ThemeDetailResDto selectOneThemeById(String themeId) {
        Theme theme = themeRepository.findById(themeId).get();

        return ThemeDetailResDto.from(theme);
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
//
//        for (String tmpId:zSetOperations.reverseRangeByScore("BOOKMARK", 0, 10)) {
//
//        };
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
