package com.rainbowgon.searchservice.domain.theme.repository;

import com.rainbowgon.searchservice.domain.theme.model.Theme;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ThemeRepository extends ElasticsearchRepository<Theme, String> {

    @Query(
            "{" +
                    "    \"bool\": {" +
                    "        \"should\": [" +
                    "            {" +
                    "                \"wildcard\": {" +
                    "                    \"venue\": {" +
                    "                        \"value\": \"*?0*\".toLowerCase()" +
                    "                    }" +
                    "                }" +
                    "            }," +
                    "            {" +
                    "                \"wildcard\": {" +
                    "                    \"explanation\": {" +
                    "                        \"value\": \"*?0*\".toLowerCase()" +
                    "                    }" +
                    "                }" +
                    "            }," +
                    "            {" +
                    "                \"wildcard\": {" +
                    "                    \"title\": {" +
                    "                        \"value\": \"*?0*\".toLowerCase()" +
                    "                    }" +
                    "                }" +
                    "            }," +
                    "            {" +
                    "                \"wildcard\": {" +
                    "                    \"location\": {" +
                    "                        \"value\": \"*?0*\".toLowerCase()" +
                    "                    }" +
                    "                }" +
                    "            }," +
                    "            {" +
                    "                \"wildcard\": {" +
                    "                    \"genre\": {" +
                    "                        \"value\": \"*?0*\".toLowerCase()" +
                    "                    }" +
                    "                }" +
                    "            }" +
                    "        ]" +
                    "    }" +
                    "}"
    )
    List<Theme> searchByKeyword(String keyword);

}
