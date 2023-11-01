package com.rainbowgon.member.domain.review.entity;

import com.rainbowgon.member.domain.bookmark.entity.EscapeStatus;
import com.rainbowgon.member.domain.bookmark.entity.SpoilStatus;
import com.rainbowgon.member.domain.profile.entity.Profile;
import com.rainbowgon.member.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", columnDefinition = "INT UNSIGNED")
    private Long id;

    @ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private Profile profile;

    @Column(name = "profile_id")
    @NotNull
    private Long profileId;

    @Column(columnDefinition = "INT UNSIGNED")
    @NotNull
    private Long themeId;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long reservationId;

    @Column(columnDefinition = "FLOAT UNSIGNED")
    @NotNull
    private Float rating; // 0.0 ~ 5.0

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


}
