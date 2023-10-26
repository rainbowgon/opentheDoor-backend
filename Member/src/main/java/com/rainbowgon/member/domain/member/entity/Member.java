package com.rainbowgon.member.domain.member.entity;

import com.rainbowgon.member.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET is_valid = 'DELETED' WHERE member_id = ?")
@Where(clause = "is_valid = 'VALID'")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "member_id", columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(columnDefinition = "VARCHAR(10)")
    @NotNull
    private String name;

    @Column(columnDefinition = "CHAR(11)")
    @NotNull
    private String phoneNumber;

    private LocalDate birthDate;

    @Builder
    public Member(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Builder
    public Member(String name, String phoneNumber, LocalDate birthDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }
}
