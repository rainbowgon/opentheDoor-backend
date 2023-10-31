package com.rainbowgon.member.domain.member.service;


import com.rainbowgon.member.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.member.domain.member.dto.request.MemberUpdateReqDto;
import com.rainbowgon.member.domain.member.dto.response.MemberInfoResDto;
import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.member.repository.MemberRepository;
import com.rainbowgon.member.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.member.domain.profile.service.ProfileService;
import com.rainbowgon.member.global.error.exception.MemberNotFoundException;
import com.rainbowgon.member.global.security.JwtTokenProvider;
import com.rainbowgon.member.global.security.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileService profileService;

    @Transactional
    @Override
    public JwtTokenDto createMember(MemberCreateReqDto createReqDto) {

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
        profileService.createProfile(member, createReqDto.getNickname(), createReqDto.getProfileImage());

        // accessToken과 refreshToken 생성
        JwtTokenDto jwtTokenDto = JwtTokenDto.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(member.getId()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(member.getId()))
                .build();

        // TODO redis에 refreshToken 저장

        return jwtTokenDto;
    }

    @Override
    public MemberInfoResDto selectMemberInfo(UUID memberId) {

        // 요청 회원의 멤버 객체 조회
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        // 프로필 정보 가져오기
        ProfileSimpleResDto profileSimpleResDto = profileService.selectProfileByMember(member.getId());

        return MemberInfoResDto.of(member, profileSimpleResDto);
    }

    @Transactional
    @Override
    public Boolean updateMemberInfo(UUID memberId, MemberUpdateReqDto memberUpdateReqDto, MultipartFile profileImage) {

        // 요청 회원의 멤버 객체 조회
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        // 프로필 서비스로 수정 요청 보내기
        if (memberUpdateReqDto.getNickname() != null || profileImage != null) {
            profileService.updateProfile(member.getId(),
                                         memberUpdateReqDto.getProfileId(),
                                         memberUpdateReqDto.getNickname(),
                                         profileImage);
        }

        // 이름, 생일 수정
        member.setName(memberUpdateReqDto.getName());
        member.setBirthDate(memberUpdateReqDto.getBirthDate());

        // 멤버 전화번호 변경됐다면 수정
        if (memberUpdateReqDto.getPhoneNumber() != null) {
            String phoneNumber = memberUpdateReqDto.getPhoneNumber();
            checkPhoneNumber(phoneNumber); // 이미 존재하는 전화번호인지 확인
            member.setPhoneNumber(phoneNumber);
        }

        return true; // try-catch로 변경하기
    }

    @Transactional
    @Override
    public void deleteMember(UUID memberId) {

        // 프로필 서비스로 삭제 요청 보내기
        profileService.delectProfile(memberId);

        // 회원 삭제
        memberRepository.deleteById(memberId);
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
