package com.ikdaman.domain.member.controller;

import com.ikdaman.domain.member.entity.Member;
import com.ikdaman.domain.member.model.MemberReq;
import com.ikdaman.domain.member.model.MemberRes;
import com.ikdaman.domain.member.service.MemberService;
import com.ikdaman.global.auth.model.AuthMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * 회원 컨트롤러
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 회원 생성 테스트
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Member> createMember(@RequestBody MemberReq dto) {
        Member member = memberService.createMember(dto);
        System.out.println("aaaaaaaaa");
        return ResponseEntity.ok(member);
    }

    // 회원 조회 테스트
    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMember(@PathVariable UUID memberId) {
        Optional<Member> member = memberService.findMemberById(memberId);
        return member.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 닉네임 중복 확인
    // TODO: 닉네임 형식에 대한 Validation (2025.05.01 기준 미정)
    @GetMapping("/check")
    public ResponseEntity checkNickname(@RequestParam(name="nickname")
                                        @NotBlank(message = "닉네임은 빈 값일 수 없습니다.")
                                            String nickname) {
        Map<String, Boolean> result = new HashMap<>();
        result.put("available", memberService.isAvailableNickname(nickname));
        return ResponseEntity.ok(result);
    }

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity findMyInfo(@AuthenticationPrincipal AuthMember authMember) {
        MemberRes result = memberService.getMember(authMember.getMember().getMemberId());
        return ResponseEntity.ok(result);
    }

    // 내 정보 수정
    @PutMapping("/me")
    public ResponseEntity editMyInfo(@AuthenticationPrincipal AuthMember authMember,
                                     @RequestBody MemberReq memberReq) {
        MemberRes result = memberService.editMember(authMember.getMember().getMemberId(), memberReq);
        return ResponseEntity.ok(result);
    }

    // 회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity signout(HttpServletRequest request) {
        UUID memberId = (UUID) request.getAttribute("memberId");
        memberService.withdrawMember(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
