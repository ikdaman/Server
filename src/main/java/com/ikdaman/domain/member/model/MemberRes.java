package com.ikdaman.domain.member.model;

import com.ikdaman.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MemberRes {
    private String nickname;
    private Member.Gender gender;
    private LocalDate birthdate;
}
