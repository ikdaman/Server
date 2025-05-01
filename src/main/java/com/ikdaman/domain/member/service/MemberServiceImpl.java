package com.ikdaman.domain.member.service;

import com.ikdaman.domain.member.entity.Member;
import com.ikdaman.domain.member.model.MemberReq;
import com.ikdaman.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * 회원 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Member createMember(MemberReq dto) {

        // 중복된 닉네임 체크
        if (memberRepository.findByNickname(dto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        Member member = dto.toEntity();
        return memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findMemberById(UUID memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkNickname(String nickname) {
        return !memberRepository.existsByNickname(nickname);
    }
}
