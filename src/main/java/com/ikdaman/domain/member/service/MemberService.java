package com.ikdaman.domain.member.service;

import com.ikdaman.domain.member.entity.Member;
import com.ikdaman.domain.member.model.MemberReq;

import java.util.Optional;
import java.util.UUID;

/**
 * 회원 서비스
 */
public interface MemberService {
    Member createMember(MemberReq dto);
    Optional<Member> findMemberById(UUID memberId);

    Boolean checkNickname(String nickname);
}
