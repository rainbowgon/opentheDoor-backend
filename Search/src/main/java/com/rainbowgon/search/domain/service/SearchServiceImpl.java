package com.rainbowgon.search.domain.service;

import com.rainbowgon.search.domain.dto.request.ThemeCreateRequestDto;
import com.rainbowgon.search.domain.dto.response.ThemeSimpleResponseDto;
import com.rainbowgon.search.domain.model.Theme;
import com.rainbowgon.search.domain.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl {

    private final ThemeRepository themeRepository;

    public List<ThemeSimpleResponseDto> searchThemes(
            String keyword,
            Integer page,
            Integer size
    ) {
        Page<Theme> pageTheme = themeRepository.searchByKeyword(keyword, PageRequest.of(page, size));

//        List<ThemeSimpleResponseDto> themeList = );
//        final Page<EsThemes> pagedEsThemes = search(keyword, page, size);
//        final long totalThemessCnt = pagedEsThemes.getTotalElements();
//        final int totalPageCnt = pagedEsThemes.getTotalPages();
//
//        final List<SearchThemes> searchThemes = new ArrayList<>();
//        for (EsThemes themeCreateRequestDto : pagedEsThemes) {
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
                .map(ThemeSimpleResponseDto::from)
                .collect(Collectors.toList());
    }

    public void insertDocument(ThemeCreateRequestDto themeCreateRequestDto) {
        final Theme theme = Theme.of(themeCreateRequestDto);
        themeRepository.save(theme);
    }

//    public void updateDocument(String themeId, ThemeCreateRequestDto themeCreateRequestDto) {
//        final EsThemes oldEsThemes = themeRepository
//                .findEsThemesByThemesUuid(promptUuid)
//                .orElseThrow(EsThemesNotFoundException::new);
//        themeRepository.delete(oldEsThemes);
//        final EsThemes newEsThemes = EsThemes.of(themeCreateRequestDto);
//        themeRepository.save(newEsThemes);
//    }

    public void deleteDocument(String themeId) {
        final Theme themeCreateRequestDto = themeRepository
                .findEsThemesByThemesUuid(promptUuid)
                .orElseThrow(EsThemesNotFoundException::new);
        themeRepository.delete(themeCreateRequestDto);
    }

    private Page<EsThemes> search(
            String keyword,
            String category,
            Pageable pageable
    ) {
        Page<EsThemes> pagedEsThemes = null;
        keyword = (keyword.equals("")) ? null : keyword;
        category = (category.equals("ALL")) ? null : category;

        if (null != keyword & null != category) {
            pagedEsThemes = themeRepository
                    .findByKeywordAndCategory(keyword, category, pageable);

        } else if (null == keyword & null != category) {
            pagedEsThemes = themeRepository
                    .findByCategory(category, pageable);

        } else if (null != keyword & null == category) {
            pagedEsThemes = themeRepository
                    .findByKeywordOnly(keyword, pageable);

        } else if (null == keyword & null == category) {
            pagedEsThemes = themeRepository
                    .findAll(pageable);
        }

        return pagedEsThemes;
    }
}
