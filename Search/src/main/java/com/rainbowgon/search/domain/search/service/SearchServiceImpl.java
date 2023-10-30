package com.rainbowgon.search.domain.search.service;

import com.rainbowgon.search.domain.search.dto.request.ThemeCreateRequestDto;
import com.rainbowgon.search.domain.search.dto.response.ThemeSimpleResponseDto;
import com.rainbowgon.search.domain.search.model.Theme;
import com.rainbowgon.search.domain.search.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ThemeRepository themeRepository;

    public List<ThemeSimpleResponseDto> searchThemes(String keyword, Integer page, Integer size) {

//        Page<Theme> pageTheme = themeRepository.searchByKeyword(keyword, PageRequest.of(page, size));
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
                .map(ThemeSimpleResponseDto::from)
                .collect(Collectors.toList());
    }

    public void insertDocument(ThemeCreateRequestDto themeCreateRequestDto) {
        final Theme theme = Theme.of(themeCreateRequestDto);
        themeRepository.save(theme);
    }

//    public void updateDocument(String themeId, ThemeCreateRequestDto themeCreateRequestDto) {
//        final Theme oldTheme = themeRepository
//                .findThemeByThemesUuid(promptUuid)
//                .orElseThrow(ThemeNotFoundException::new);
//        themeRepository.delete(oldTheme);
//        final Theme newTheme = Theme.of(themeCreateRequestDto);
//        themeRepository.save(newTheme);
//    }

    public void deleteDocument(String themeId) {
        final Theme theme = themeRepository
                .findThemeByThemeId(themeId).get();
//                .orElseThrow(ThemeNotFoundException::new);
        themeRepository.delete(theme);
    }

    public Page<Theme> search(String keyword, Pageable pageable) {
        Page<Theme> pagedTheme = null;
        keyword = (keyword.equals("")) ? null : keyword;

        if (null != keyword) {
            pagedTheme = themeRepository
                    .searchByKeyword(keyword, pageable);

        } else if (null == keyword) {
            pagedTheme = themeRepository
                    .findAll(pageable);
        }

        return pagedTheme;
    }
}
