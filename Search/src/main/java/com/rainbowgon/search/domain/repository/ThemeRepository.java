package com.rainbowgon.search.domain.repository;

import com.rainbowgon.search.domain.model.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ThemeRepository extends ElasticsearchRepository<Theme, String> {

    @Query("{" +
            "    \"bool\": {" +
            "        \"should\": [" +
            "            {" +
            "                \"multi_match\": {" +
            "                    \"query\": \"?0\"," +
            "                    \"fields\": [\"venue\", \"explanation\", \"title\", \"genre\"]" +
            "                }" +
            "            }" +
            "        ]" +
            "    }" +
            "}")
    Page<Theme> searchByKeyword(String keyword, Pageable pageable);

    Optional<Theme> findThemeByThemeId(String ThemeId);
}
