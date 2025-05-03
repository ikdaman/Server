package com.ikdaman.domain.mybook.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 첫인상 추가 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImpressionReq {
    @NotBlank(message = "첫인상을 입력해주세요!")
    public String impression;
    public String createdAt;

    @Builder
    public ImpressionReq(String impression, String createdAt) {
        this.impression = impression;
        this.createdAt = createdAt;
    }
}
