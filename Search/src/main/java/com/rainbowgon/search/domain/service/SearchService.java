package com.rainbowgon.search.domain.service;

import com.rainbowgon.search.domain.dto.response.ThemeSimpleResponseDto;

import java.util.List;

public interface SearchService {

    void saveAllDocuments();

    public List<ThemeSimpleResponseDto> searchThemes(
            String keyword,
            Integer page,
            Integer size);

//    public void insertDocument(SyncEsTheme syncEsTheme);
//
//    public void updateDocument(String themeId, SyncEsTheme syncEsTheme);
//
//    public void deleteDocument(String themeId);
}
