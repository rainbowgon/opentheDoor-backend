package com.rainbowgon.senderserver.domain.sender.entity;

import com.rainbowgon.senderserver.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", columnDefinition = "INT UNSIGNED")
    private Long id;

    private Long profileId;
    private String themeId;
    private String title;
    private String body;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ETC'")
    private NotificationType notificationType;

    @Builder
    public NotificationLog(Long profileId, String themeId, String title, String body,
                           NotificationType notificationType) {
        this.profileId = profileId;
        this.themeId = themeId;
        this.title = title;
        this.body = body;
        this.notificationType = notificationType;
    }
}