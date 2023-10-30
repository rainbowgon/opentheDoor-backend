package com.rainbowgon.member.domain.member.service;


import com.rainbowgon.member.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.member.domain.member.dto.request.MemberUpdateReqDto;
import com.rainbowgon.member.domain.member.dto.response.MemberCreateResDto;
import com.rainbowgon.member.domain.member.dto.response.MemberInfoResDto;
import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.member.repository.MemberRepository;
import com.rainbowgon.member.domain.profile.dto.response.ProfileCreateResDto;
import com.rainbowgon.member.domain.profile.entity.Profile;
import com.rainbowgon.member.domain.profile.service.ProfileService;
import com.rainbowgon.member.global.error.exception.ProfileNotFoundException;
import com.rainbowgon.member.global.error.exception.ProfileUnauthorizedException;
import com.rainbowgon.member.global.security.JwtTokenProvider;
import com.rainbowgon.member.global.security.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileService profileService;

    @Override
    public MemberCreateResDto createMember(MemberCreateReqDto createReqDto) {

        // 전화번호 중복 체크
        checkPhoneNumber(createReqDto.getPhoneNumber());

        // 클라이언트한테 받은 정보로 멤버 객체 생성 후 DB에 저장
        Member member = memberRepository.save(
                Member.builder()
                        .name(createReqDto.getName())
                        .phoneNumber(createReqDto.getPhoneNumber())
                        .provider(createReqDto.getProvider())
                        .providerId(createReqDto.getProviderId())
                        .birthDate(createReqDto.getBirthDate())
                        .build());

        // 생성한 멤버 객체로 프로필 객체 생성
        ProfileCreateResDto profile = profileService.createProfile(member, createReqDto.getNickname(), createReqDto.getProfileImage());

        // accessToken과 refreshToken 생성
        JwtTokenDto jwtTokenDto = JwtTokenDto.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(member.getId()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(member.getId()))
                .build();

        // TODO redis에 refreshToken 저장

        return MemberCreateResDto.builder()
                .memberInfo(MemberInfoResDto.of(member, profile))
                .tokens(jwtTokenDto)
                .build();
    }

    @Override
    public MemberInfoResDto selectMemberInfo(UUID memberId) {

        // 요청 회원의 프로필 조회
        Profile profile = profileService.selectProfileByMember(memberId).orElseThrow(ProfileNotFoundException::new);

        // 조회한 프로필의 회원과 요청 회원이 같은지 확인
        if (!profile.getMember().getId().equals(memberId)) {
            throw ProfileUnauthorizedException.EXCEPTION;
        }

        return MemberInfoResDto.from(profile);
    }

    @Override
    public Boolean updateMemberInfo(UUID memberId, MemberUpdateReqDto memberUpdateReqDto, MultipartFile profileImage) {

        // 유효한 프로필인지 확인
        Profile profile = getOneProfile(profileUpdateReqDto.getProfileId());

        // 유효한 접근인지 확인
        if (!profile.getMember().getId().equals(memberId)) {
            throw ProfileUnauthorizedException.EXCEPTION;
        }

        // 닉네임, 이름, 생일 수정
        profile.setNickname(profileUpdateReqDto.getNickname());
        profile.getMember().setName(profileUpdateReqDto.getName());
        profile.getMember().setBirthDate(profileUpdateReqDto.getBirthDate());

        // 프로필 사진 변경됐다면 수정
        if (profileImage != null) {
            // TODO 수정한 프로필 이미지 s3 업로드
            String profileImageUrl = null;

        }

        // 멤버 전화번호 변경됐다면 수정
        if (profileUpdateReqDto.getPhoneNumber() != null) {
            String phoneNumber = profileUpdateReqDto.getPhoneNumber();
            memberService.checkPhoneNumber(phoneNumber); // 이미 존재하는 전화번호인지 확인
            profile.getMember().setPhoneNumber(phoneNumber);
        }

        return true; // try-catch로 변경하기
    }

    /**
     * 전화번호 중복 체크
     * 이미 존재하는 전화번호이면, 기존 회원 전화번호 앞자리 999로 변경
     */
    private void checkPhoneNumber(String phoneNumber) {

        Optional<Member> member = memberRepository.findByPhoneNumber(phoneNumber);

        if (member.isPresent()) {
            Member targetMember = member.get();
            String originPhoneNumber = targetMember.getPhoneNumber();
            String newPhoneNumber = "999" + originPhoneNumber.substring(3);
            targetMember.setPhoneNumber(newPhoneNumber);
        }
    }

}
