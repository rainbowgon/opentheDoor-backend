package com.rainbowgon.member.domain.profile.entity;

import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", columnDefinition = "INT UNSIGNED")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(columnDefinition = "VARCHAR(10)")
    @NotNull
    private String nickname;

    @NotNull
    private String profileImage;
}
