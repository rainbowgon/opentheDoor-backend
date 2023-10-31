package com.rainbowgon.member.domain.profile.entity;

import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Profile SET is_valid = 'DELETED' WHERE profile_id = ?")
@Where(clause = "is_valid = 'VALID'")
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", columnDefinition = "INT UNSIGNED")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "VARCHAR(10)")
    @NotNull
    private String nickname; // 중복 허용

    //    @NotNull
    private String profileImage;

    @Column(columnDefinition = "VARCHAR(5) DEFAULT 'ON'")
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus = NotificationStatus.ON; // 앱 내 전체 알림 on(true)/off(false)

    @Builder
    public Profile(Member member, String nickname, String profileImage) {
        this.member = member;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setNotificationStatus(NotificationStatus status) {
        this.notificationStatus = status;
    }

}
