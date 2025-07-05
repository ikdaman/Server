package com.ikdaman.domain.notice.controller;

import com.ikdaman.domain.notice.model.NoticeListRes;
import com.ikdaman.domain.notice.model.NoticeReq;
import com.ikdaman.domain.notice.model.NoticeRes;
import com.ikdaman.domain.notice.service.NoticeService;
import com.ikdaman.global.auth.model.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 등록
    @PostMapping
    public ResponseEntity<NoticeReq> addNotice(
            @AuthenticationPrincipal AuthMember authMember,
            @RequestBody NoticeReq request) {
        noticeService.addNotice(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<NoticeListRes> getNotices(
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "9") Integer limit) {
        return ResponseEntity.ok(noticeService.getNotices(page, limit));
    }

    // 공지사항 상세 조회
    @GetMapping("/{notice_id}")
    public ResponseEntity<NoticeRes> getNotice(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("notice_id") Long noticeId) {
        return ResponseEntity.ok(noticeService.getNotice(noticeId));
    }
}
