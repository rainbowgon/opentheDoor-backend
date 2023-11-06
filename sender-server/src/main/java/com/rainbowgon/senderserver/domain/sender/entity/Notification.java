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
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", columnDefinition = "INT UNSIGNED")
    private Long id;

    private Long profileId;
    private Long themeId;
    private String title;
    private String body;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ETC'")
    private Type type;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NOT_VIEWED'")
    private ViewStatus isViewed;

    @Builder
    public Notification(Long profileId, Long themeId, String title, String body, Type type,
                        ViewStatus isViewed) {
        this.profileId = profileId;
        this.themeId = themeId;
        this.title = title;
        this.body = body;
        this.type = type;
        this.isViewed = isViewed;
    }
}
