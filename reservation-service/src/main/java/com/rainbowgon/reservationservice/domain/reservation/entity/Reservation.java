package com.rainbowgon.reservationservice.domain.reservation.entity;

import com.rainbowgon.reservationservice.global.entity.BaseEntity;
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
    private Long reservationNumber; // 예약 번호

    private LocalDate targetDate;

    private LocalTime targetTime;

    @Column(columnDefinition = "INT UNSIGNED")
    private Long headcount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(12)")
    private MemberVerifiedStatus isMemberVerified;

    @Column(columnDefinition = "VARCHAR(20)")
    private String bookerName;

    @Column(columnDefinition = "CHAR(11)")
    private String bookerPhoneNumber;

    private String themeId; // 테마 ID

    @Column(columnDefinition = "INT UNSIGNED")
    private Long bookerId; // 프로필 ID
}
