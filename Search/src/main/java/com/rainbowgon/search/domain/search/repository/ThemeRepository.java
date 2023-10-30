package com.rainbowgon.search.domain.search.repository;

import com.rainbowgon.search.domain.search.model.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ThemeRepository extends ElasticsearchRepository<Theme, String> {

    //    @Query("{" +
//            "    \"bool\": {" +
//            "        \"must\": [" +
//            "            {" +
//            "                \"multi_match\": {" +
//            "                    \"query\": \"?0\"," +
//            "                    \"fields\": [\"venue.nori\", \"explanation.nori\", \"title.nori\", \"genre.nori\"]" +
//            "                }" +
//            "            }," +
//            "            {" +
//            "                \"multi_match\": {" +
//            "                    \"query\": \"?0\"," +
//            "                    \"fields\": [\"venue.ngram\", \"explanation.ngram\", \"title.ngram\", \"genre.ngram\"]" +
//            "                }" +
//            "            }" +
//            "        ]" +
//            "    }" +
//            "}")
    @Query("{" +
            "    \"bool\": {" +
            "        \"should\": [" +
            "            {" +
            "                \"wildcard\": {" +
            "                    \"venue\": {" +
            "                        \"value\": \"*?0*\"" +
            "                    }" +
            "                }" +
            "            }," +
            "            {" +
            "                \"wildcard\": {" +
            "                    \"explanation\": {" +
            "                        \"value\": \"*?0*\"" +
            "                    }" +
            "                }" +
            "            }," +
            "            {" +
            "                \"wildcard\": {" +
            "                    \"title\": {" +
            "                        \"value\": \"*?0*\"" +
            "                    }" +
            "                }" +
            "            }," +
            "            {" +
            "                \"wildcard\": {" +
            "                    \"genre\": {" +
            "                        \"value\": \"*?0*\"" +
            "                    }" +
            "                }" +
            "            }" +
            "        ]" +
            "    }" +
            "}")
    Page<Theme> searchByKeyword(String keyword, Pageable pageable);

    Optional<Theme> findThemeByThemeId(String ThemeId);
}
