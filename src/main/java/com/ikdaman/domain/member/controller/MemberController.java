package com.ikdaman.domain.member.controller;

import com.ikdaman.domain.member.entity.Member;
import com.ikdaman.domain.member.model.MemberReq;
import com.ikdaman.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
