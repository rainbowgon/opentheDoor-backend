package com.rainbowgon.search.domain.theme.model;

import com.rainbowgon.search.domain.theme.dto.request.ThemeCreateReqDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Document(indexName = "themes")
public class Theme {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "venue")
    private String venue;

    @Field(type = FieldType.Text, name = "title")
    private String title;

    @Field(type = FieldType.Text, name = "explanation")
    private String explanation;

    @Field(type = FieldType.Keyword, name = "poster")
    private String poster;

    @Field(type = FieldType.Keyword, name = "genre", index = false)
    private String[] genre;

    @Field(type = FieldType.Integer, name = "level")
    private Integer level;

    @Field(type = FieldType.Integer, name = "minHeadcount")
    private Integer minHeadcount;

    @Field(type = FieldType.Integer, name = "maxHeadcount")
    private Integer maxHeadcount;

    public static Theme of(ThemeCreateReqDto syncTheme) {
        return Theme.builder()
                .venue(syncTheme.getVenue())
                .title(syncTheme.getTitle())
                .explanation(syncTheme.getExplanation())
                .poster(syncTheme.getPoster())
                .genre(syncTheme.getGenre())
                .level(syncTheme.getLevel())
                .minHeadcount(syncTheme.getMinHeadcount())
                .maxHeadcount(syncTheme.getMaxHeadcount())
                .build();
    }

}