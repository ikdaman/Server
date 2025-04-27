package com.ikdaman.domain.member.model;

import com.ikdaman.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * 회원 요청 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReq {

    @NotBlank
    @Size(min=1, max=20)
    private String nickname;
    private Member.Gender gender;
    private LocalDate birthdate;

    @Builder
    public MemberReq(String nickname, Member.Gender gender, LocalDate birthdate) {
        this.nickname = nickname;
        this.gender = gender;
        this.birthdate = birthdate;
    }

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .gender(gender)
                .birthdate(birthdate)
                .build();
    }
}
