package com.rainbowgon.searchservice.domain.theme.model;

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

    @Field(type = FieldType.Text, name = "location")
    private String location;

    @Field(type = FieldType.Text, name = "reservationNotice", index = false)
    private String reservationNotice;
    // 검색에 사용되지 않을 필드는 index = false를 설정합니다.
    @Field(type = FieldType.Keyword, name = "poster", index = false)
    private String poster;

    @Field(type = FieldType.Keyword, name = "tel", index = false)
    private String tel;

    // genre 필드에 대해 검색을 원하므로 index를 true로 설정합니다 (index = true는 기본값이므로 생략 가능).
    @Field(type = FieldType.Keyword, name = "genre")
    private String[] genre;

    @Field(type = FieldType.Double, name = "level", index = false)
    private Double level;

    @Field(type = FieldType.Integer, name = "minHeadcount", index = false)
    private Integer minHeadcount;

    @Field(type = FieldType.Integer, name = "maxHeadcount", index = false)
    private Integer maxHeadcount;

    @Field(type = FieldType.Integer, name = "timeLimit", index = false)
    private Integer timeLimit;

    @Field(type = FieldType.Integer, name = "price", index = false)
    private Integer price;

    @Field(type = FieldType.Double, name = "activity", index = false)
    private Double activity;

    @Field(type = FieldType.Double, name = "lockRatio", index = false)
    private Double lockRatio;

    @Field(type = FieldType.Double, name = "horror", index = false)
    private Double horror;


}