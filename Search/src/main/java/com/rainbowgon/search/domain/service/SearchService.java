package com.rainbowgon.search.domain.service;

import com.rainbowgon.search.domain.dto.request.ThemeCreateRequestDto;
import com.rainbowgon.search.domain.dto.response.ThemeSimpleResponseDto;
import com.rainbowgon.search.domain.model.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {

//    void saveAllDocuments();

    public List<ThemeSimpleResponseDto> searchThemes(
            String keyword,
            Integer page,
            Integer size);

    public void insertDocument(ThemeCreateRequestDto themeCreateRequestDto);

    //
//    public void updateDocument(String themeId, SyncEsTheme syncEsTheme);
//
    public void deleteDocument(String themeId);

    public Page<Theme> search(String keyword,
                              Pageable pageable);
}
