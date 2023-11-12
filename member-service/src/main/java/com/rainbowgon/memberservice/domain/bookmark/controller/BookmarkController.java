package com.rainbowgon.memberservice.domain.bookmark.controller;

import com.rainbowgon.memberservice.domain.bookmark.dto.request.BookmarkUpdateReqDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkDetailResDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkSimpleResDto;
import com.rainbowgon.memberservice.domain.bookmark.service.BookmarkService;
import com.rainbowgon.memberservice.global.response.JsonResponse;
import com.rainbowgon.memberservice.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    /**
     * 요청 회원의 북마크 목록을 업데이트.
     * 회원의 북마크 목록 중 변경이 있는 테마 ID 리스트 전체를 요청 인자로 받아 업데이트
     */
    @PutMapping
    public ResponseEntity<ResponseWrapper<Nullable>> updateBookmarkList(
            @RequestHeader("memberId") String memberId,
            @RequestBody BookmarkUpdateReqDto bookmarkUpdateReqDto) {

        bookmarkService.updateBookmarkList(UUID.fromString(memberId), bookmarkUpdateReqDto);

        return JsonResponse.ok("북마크 목록을 성공적으로 업데이트 하였습니다.");
    }

    /**
     * 홈 화면에서 보여지는 북마크 목록을 조회.
     * 회원의 전체 북마크 중 최신순 20개 반환
     * -> 테마 ID, 테마 포스터, 제목, 지점명, 평균 별점, 리뷰 수
     */
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<BookmarkSimpleResDto>>> selectSimpleBookmarkList(
            @RequestHeader("memberId") String memberId) {

        List<BookmarkSimpleResDto> bookmarkList = bookmarkService.selectSimpleBookmarkList(UUID.fromString(memberId));

        // TODO pageInfo 추가
        return JsonResponse.ok("홈 화면의 북마크 목록을 성공적으로 조회했습니다.", bookmarkList);
    }

    /**
     * 마이페이지의 북마크 내역에서 보여지는 북마크 목록을 조회.
     * 회원의 전체 북마크 반환.
     * -> 테마와 관련된 모든 데이터
     */
    @GetMapping("/detail")
    public ResponseEntity<ResponseWrapper<List<BookmarkDetailResDto>>> selectDetailBookmarkList(
            @RequestHeader("memberId") String memberId) {

        List<BookmarkDetailResDto> bookmarkList = bookmarkService.selectDetailBookmarkList(UUID.fromString(memberId));

        // TODO pageInfo 추가
        return JsonResponse.ok("마이페이지의 북마크 목록을 성공적으로 조회했습니다.", bookmarkList);
    }

    /**
     * 북마크 한 테마 예약 오픈 알림 on/off
     */
    @PatchMapping("/notifications/{theme-id}")
    public ResponseEntity<ResponseWrapper<String>> updateNotificationStatus(
            @RequestHeader("memberId") String memberId,
            @PathVariable("theme-id") String themeId) {

        String status = bookmarkService.updateNotificationStatus(UUID.fromString(memberId), themeId);

        return JsonResponse.ok("선택한 테마의 오픈 알림 설정이 변경되었습니다.", status);
    }


}
