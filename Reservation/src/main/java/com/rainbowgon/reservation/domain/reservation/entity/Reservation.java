package com.rainbowgon.reservation.domain.reservation.entity;

import com.rainbowgon.reservation.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", columnDefinition = "INT UNSIGNED")
    private Long id;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long reservationNumber;

    private LocalDate targetDate;

    private LocalTime targetTime;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long headCount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(12)")
    private VerifiedStatus isVerified;

    @Column(columnDefinition = "VARCHAR(20)")
    private String bookerName;

    @Column(columnDefinition = "CHAR(11)")
    private String bookerPhoneNumber;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long themeId;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long bookerId;
}
