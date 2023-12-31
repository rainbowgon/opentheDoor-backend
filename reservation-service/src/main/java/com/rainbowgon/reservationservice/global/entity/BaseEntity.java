package com.rainbowgon.reservationservice.global.entity;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'VALID'")
    @Column(columnDefinition = "VARCHAR(10)")
    private ValidStatus isValid = ValidStatus.VALID;
}
