package com.rainbowgon.memberservice.domain.bookmark.entity;

import com.rainbowgon.memberservice.domain.profile.entity.Profile;
import com.rainbowgon.memberservice.global.entity.BaseEntity;
import com.rainbowgon.memberservice.global.entity.NotificationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id", columnDefinition = "INT UNSIGNED")
    private Long id;

    @ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private Profile profile;

    @Column(name = "profile_id")
    @NotNull
    private Long profileId;

    @NotNull
    private Long themeId;

    @Column(columnDefinition = "VARCHAR(5) DEFAULT 'ON'")
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus = NotificationStatus.ON; // 예약 오픈 알림 on/off

    @Builder
    public Bookmark(Long profileId, Long themeId) {
        this.profileId = profileId;
        this.themeId = themeId;
    }

    public void updateNotificationStatus(NotificationStatus status) {
        this.notificationStatus = status;
    }
}
