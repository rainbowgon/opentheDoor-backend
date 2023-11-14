package com.rainbowgon.memberservice.domain.review.entity;

import com.rainbowgon.memberservice.domain.member.entity.Member;
import com.rainbowgon.memberservice.domain.review.dto.request.ReviewUpdateReqDto;
import com.rainbowgon.memberservice.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Review SET is_valid = 'DELETED' WHERE review_id = ?")
@Where(clause = "is_valid = 'VALID'")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", columnDefinition = "INT UNSIGNED")
    private Long id;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Column(name = "member_id", columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    @NotNull
    private UUID memberId;

    @NotNull
    private String themeId;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long reservationId;

    @Column(columnDefinition = "DOUBLE UNSIGNED")
    @NotNull
    private Double rating; // 0.0 ~ 5.0

    @Column(columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private EscapeStatus isEscaped;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer myLevel; // 1 ~ 5

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer hintCount;

    private String content;

    @Column(columnDefinition = "VARCHAR(5) DEFAULT 'NO'")
    @Enumerated(EnumType.STRING)
    private SpoilStatus isSpoiler;

    private LocalDate performedDate;

    private LocalTime performedTime;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer performedHeadcount;

    @Builder
    public Review(UUID memberId, String themeId, Long reservationId, Double rating, EscapeStatus isEscaped,
                  Integer myLevel, Integer hintCount, String content, SpoilStatus isSpoiler,
                  LocalDate performedDate, LocalTime performedTime, Integer performedHeadcount) {
        this.memberId = memberId;
        this.themeId = themeId;
        this.reservationId = reservationId;
        this.rating = rating;
        this.isEscaped = isEscaped;
        this.myLevel = myLevel;
        this.hintCount = hintCount;
        this.content = content;
        this.isSpoiler = isSpoiler;
        this.performedDate = performedDate;
        this.performedTime = performedTime;
        this.performedHeadcount = performedHeadcount;
    }

    public void updateReview(ReviewUpdateReqDto reviewUpdateReqDto) {
        this.rating = reviewUpdateReqDto.getRating();
        this.isEscaped = reviewUpdateReqDto.getIsEscape();
        this.myLevel = reviewUpdateReqDto.getMyLevel();
        this.hintCount = reviewUpdateReqDto.getHintCount();
        this.content = reviewUpdateReqDto.getContent();
        this.isSpoiler = reviewUpdateReqDto.getIsSpoiler();
        this.performedDate = reviewUpdateReqDto.getPerformedDate();
        this.performedTime = reviewUpdateReqDto.getPerformedTime();
        this.performedHeadcount = reviewUpdateReqDto.getPerformedHeadcount();
    }

}
