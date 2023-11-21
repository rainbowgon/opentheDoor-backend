package com.rainbowgon.memberservice.domain.member.service;


import com.rainbowgon.memberservice.domain.bookmark.service.BookmarkService;
import com.rainbowgon.memberservice.domain.member.dto.MemberDto;
import com.rainbowgon.memberservice.domain.member.dto.request.MemberCreateReqDto;
import com.rainbowgon.memberservice.domain.member.dto.request.MemberPhoneReqDto;
import com.rainbowgon.memberservice.domain.member.dto.request.MemberUpdateReqDto;
import com.rainbowgon.memberservice.domain.member.dto.response.BookerInfoResDto;
import com.rainbowgon.memberservice.domain.member.dto.response.LoginResDto;
import com.rainbowgon.memberservice.domain.member.dto.response.MemberInfoResDto;
import com.rainbowgon.memberservice.domain.member.entity.Member;
import com.rainbowgon.memberservice.domain.member.repository.MemberRepository;
import com.rainbowgon.memberservice.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.memberservice.domain.profile.service.ProfileService;
import com.rainbowgon.memberservice.global.client.dto.output.FcmTokenListOutDto;
import com.rainbowgon.memberservice.global.client.dto.output.FcmTokenOutDto;
import com.rainbowgon.memberservice.global.error.exception.MemberBadPhoneNumberException;
import com.rainbowgon.memberservice.global.error.exception.MemberNotFoundException;
import com.rainbowgon.memberservice.global.error.exception.RedisErrorException;
import com.rainbowgon.memberservice.global.redis.dto.Token;
import com.rainbowgon.memberservice.global.redis.repository.TokenRedisRepository;
import com.rainbowgon.memberservice.global.util.CoolSmsSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ProfileService profileService;
    private final BookmarkService bookmarkService;
    private final CoolSmsSender coolSmsSender;
    private final TokenRedisRepository tokenRedisRepository;

    @Transactional
    @Override
    public LoginResDto createMember(MemberCreateReqDto createReqDto) {

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

        // 생성한 멤버 객체로 프로필 객체 생성 후 토큰 반환
        return profileService.createProfile(
                member, createReqDto.getFcmToken(), createReqDto.getNickname(), createReqDto.getProfileImage());
    }

    @Override
    public Integer sendMessage(MemberPhoneReqDto memberPhoneReqDto) {

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
    public void updateMemberInfo(UUID memberId, MemberUpdateReqDto memberUpdateReqDto, MultipartFile profileImage) {

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
    }

    @Transactional
    @Override
    public void deleteMember(UUID memberId) {

        // 프로필 서비스로 삭제 요청 보내기
        profileService.deleteProfile(memberId);

        // 회원 삭제
        memberRepository.deleteById(memberId);

        // 회원의 북마크 내역 삭제 요청 보내기
        bookmarkService.deleteBookmark(memberId);
    }

    @Override
    public void logout(UUID memberId) {

        // 회원 ID로 프로필 ID 조회
        ProfileSimpleResDto profile = profileService.selectProfileByMember(memberId);

        // 토큰 삭제하기
        tokenRedisRepository.deleteById(profile.getProfileId());
    }

    /**
     * reservation-service와 feign 통신
     * memberId로 name, phoneNumber 조회
     */
    @Override
    public BookerInfoResDto selectBookerInfo(String memberId) {

        Member member = memberRepository.findById(UUID.fromString(memberId)).orElseThrow(MemberNotFoundException::new);

        return BookerInfoResDto.of(member.getName(), member.getPhoneNumber());
    }

    /**
     * 회원 ID로 fcm token 단 건 조회
     */
    @Override
    public FcmTokenOutDto selectMemberFcmToken(String memberId) {

        // 회원 ID로 프로필 ID 조회
        ProfileSimpleResDto profile = profileService.selectProfileByMember(UUID.fromString(memberId));

        // redis token 가져오기
        Token token = tokenRedisRepository.findById(profile.getProfileId()).orElseThrow(RedisErrorException::new);

        return FcmTokenOutDto.from(token);
    }

    /**
     * 회원 ID 리스트로 fcm token 리스트 조회
     */
    @Override
    public FcmTokenListOutDto selectMemberFcmTokenList(List<String> memberIdList) {

        List<FcmTokenOutDto> fcmTokenList = memberIdList.stream()
                .map(memberId -> profileService.selectProfileByMember(UUID.fromString(memberId)))
                .map(profile -> tokenRedisRepository.findById(profile.getProfileId()).orElseThrow(RedisErrorException::new))
                .map(FcmTokenOutDto::from)
                .collect(Collectors.toList());

        return FcmTokenListOutDto.builder().fcmTokenList(fcmTokenList).build();
    }

    /**
     * OAuth Provider ID로 회원 조회
     * 존재하는 회원이면 회원 ID, 프로필 ID 반환
     */
    @Override
    public MemberDto findMemberByProviderId(String providerId) {

        // OAuth ID로 회원 조회
        Optional<Member> member = memberRepository.findByProviderId(providerId);

        // 없는 회원이면 null 반환 -> 회원가입
        if (member.isEmpty()) {
            return null;
        }

        // 회원 ID로 프로필 조회
        ProfileSimpleResDto profile = profileService.selectProfileByMember(member.get().getId());

        return MemberDto.of(member.get(), profile.getProfileId());
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
