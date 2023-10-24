package com.rainbowgon.member.domain.member.entity;

import com.rainbowgon.member.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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
}
