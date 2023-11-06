package com.rainbowgon.memberservice.domain.review.dto.request;

import com.rainbowgon.memberservice.domain.review.entity.EscapeStatus;
import com.rainbowgon.memberservice.domain.review.entity.SpoilStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewUpdateReqDto {

    private Long reviewId;
    private Double rating;
    private EscapeStatus isEscape;
    private Integer myLevel;
    private Integer hintCount;
    private String content;
    private SpoilStatus isSpoiler;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate performedDate;
    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime performedTime;
    private Integer performedHeadcount;
}
