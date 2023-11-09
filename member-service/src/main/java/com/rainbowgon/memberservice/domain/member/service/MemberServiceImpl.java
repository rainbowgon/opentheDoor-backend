package com.rainbowgon.memberservice.domain.member.service;


import com.rainbowgon.memberservice.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.memberservice.domain.member.dto.request.MemberPhoneReqDto;
import com.rainbowgon.memberservice.domain.member.dto.request.MemberUpdateReqDto;
import com.rainbowgon.memberservice.domain.member.dto.response.MemberInfoResDto;
import com.rainbowgon.memberservice.domain.member.entity.Member;
import com.rainbowgon.memberservice.domain.member.repository.MemberRepository;
import com.rainbowgon.memberservice.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.memberservice.domain.profile.service.ProfileService;
import com.rainbowgon.memberservice.global.error.exception.MemberBadPhoneNumberException;
import com.rainbowgon.memberservice.global.error.exception.MemberNotFoundException;
import com.rainbowgon.memberservice.global.security.JwtTokenProvider;
import com.rainbowgon.memberservice.global.security.dto.JwtTokenDto;
import com.rainbowgon.memberservice.global.util.CoolSmsSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileService profileService;
    private final CoolSmsSender coolSmsSender;

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
    public String sendMessage(MemberPhoneReqDto memberPhoneReqDto) {

        String phoneNumber = memberPhoneReqDto.getPhoneNumber();

        // phoneNumber 형식 검증 (01012345678)
        checkPhoneNumberFormat(phoneNumber);

        return coolSmsSender.sendOne(phoneNumber);
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
            profileService.updateProfile(
                    member.getId(), memberUpdateReqDto.getProfileId(), memberUpdateReqDto.getNickname(), profileImage);
        }

        // 이름, 생일 수정
        member.updateName(memberUpdateReqDto.getName());
        member.updateBirthDate(memberUpdateReqDto.getBirthDate());

        // 멤버 전화번호 변경됐다면 수정
        if (memberUpdateReqDto.getPhoneNumber() != null) {
            String phoneNumber = memberUpdateReqDto.getPhoneNumber();
            checkPhoneNumber(phoneNumber); // 이미 존재하는 전화번호인지 확인
            member.updatePhoneNumber(phoneNumber);
        }

        return true; // try-catch로 변경하기
    }

    @Transactional
    @Override
    public void deleteMember(UUID memberId) {

        // 프로필 서비스로 삭제 요청 보내기
        profileService.deleteProfile(memberId);

        // 회원 삭제
        memberRepository.deleteById(memberId);
    }

    /**
     * 전화번호 중복 체크
     * 중복값 있으면, 기존 회원의 전화번호 변경 로직 수행
     */
    private void checkPhoneNumber(String phoneNumber) {
        memberRepository.findByPhoneNumber(phoneNumber).ifPresent(this::overwritePhoneNumber);
    }

    /**
     * 전화번호 중복 시,
     * 기존 전화번호의 앞자리를 999로 변경
     */
    private void overwritePhoneNumber(Member member) {
        String originPhoneNumber = member.getPhoneNumber();
        String newPhoneNumber = "999" + originPhoneNumber.substring(3);
        member.updatePhoneNumber(newPhoneNumber);
    }

    /**
     * 전화번호 포맷 검증
     * 11자리 숫자, 010으로 시작
     */
    private void checkPhoneNumberFormat(String phoneNumber) {
        String phoneNumberPattern = "^010(\\d{4})(\\d{4})$";
        if (!Pattern.matches(phoneNumberPattern, phoneNumber)) {
            throw MemberBadPhoneNumberException.EXCEPTION;
        }
    }

}
