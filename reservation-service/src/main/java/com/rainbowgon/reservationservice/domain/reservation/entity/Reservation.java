package com.rainbowgon.reservationservice.domain.reservation.entity;

import com.rainbowgon.reservationservice.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime targetTime;

    @Column(columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer headcount;

    @Column(columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(12)")
    private MemberVerifiedStatus isMemberVerified; // 회원인지, 비회원인지

    @Column(columnDefinition = "VARCHAR(20)")
    private String bookerName;

    @Column(columnDefinition = "CHAR(11)")
    private String bookerPhoneNumber;

    private String themeId; // 테마 ID

    @Column(columnDefinition = "CHAR(36)")
    private String memberId; // 멤버 ID / 비회원인 경우, null

    @Builder
    public Reservation(Long reservationNumber, LocalDate targetDate, LocalTime targetTime,
                       Integer headcount, Integer totalPrice, MemberVerifiedStatus isMemberVerified,
                       String bookerName, String bookerPhoneNumber, String themeId, String memberId) {
        this.reservationNumber = reservationNumber;
        this.targetDate = targetDate;
        this.targetTime = targetTime;
        this.headcount = headcount;
        this.totalPrice = totalPrice;
        this.isMemberVerified = isMemberVerified;
        this.bookerName = bookerName;
        this.bookerPhoneNumber = bookerPhoneNumber;
        this.themeId = themeId;
        this.memberId = memberId;
    }

    public void updateReservationNumber() {
        this.reservationNumber = this.id + 1000;
    }
}
