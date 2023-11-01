package com.rainbowgon.search.domain.theme.repository;

import com.rainbowgon.search.domain.theme.model.Theme;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ThemeRepository extends ElasticsearchRepository<Theme, String> {

    @Query("{" +
            "    \"bool\": {" +
            "        \"should\": [" +
            "            {" +
            "                \"wildcard\": {" +
            "                    \"venue.nori\": {" +
            "                        \"value\": \"*?0*\"" +
            "                    }" +
            "                }" +
            "            }," +
            "            {" +
            "                \"wildcard\": {" +
            "                    \"explanation.nori\": {" +
            "                        \"value\": \"*?0*\"" +
            "                    }" +
            "                }" +
            "            }," +
            "            {" +
            "                \"wildcard\": {" +
            "                    \"title.nori\": {" +
            "                        \"value\": \"*?0*\"" +
            "                    }" +
            "                }" +
            "            }," +
            "            {" +
            "                \"wildcard\": {" +
            "                    \"genre.nori\": {" +
            "                        \"value\": \"*?0*\"" +
            "                    }" +
            "                }" +
            "            }" +
            "        ]" +
            "    }" +
            "}")
    List<Theme> searchByKeyword(String keyword);

//    @Query("{" +
//            "    \"bool\": {" +
//            "        \"should\": [" +
//            "            {" +
//            "                \"wildcard\": {" +
//            "                    \"venue.nori\": {" +
//            "                        \"value\": \"*?0*\"" +
//            "                    }" +
//            "                }" +
//            "            }," +
//            "            {" +
//            "                \"wildcard\": {" +
//            "                    \"explanation.nori\": {" +
//            "                        \"value\": \"*?0*\"" +
//            "                    }" +
//            "                }" +
//            "            }," +
//            "            {" +
//            "                \"wildcard\": {" +
//            "                    \"title.nori\": {" +
//            "                        \"value\": \"*?0*\"" +
//            "                    }" +
//            "                }" +
//            "            }," +
//            "            {" +
//            "                \"wildcard\": {" +
//            "                    \"genre.nori\": {" +
//            "                        \"value\": \"*?0*\"" +
//            "                    }" +
//            "                }" +
//            "            }" +
//            "        ]" +
//            "    }" +
//            "}")
//    Page<Theme> searchByKeyword(String keyword, Pageable pageable);


}
