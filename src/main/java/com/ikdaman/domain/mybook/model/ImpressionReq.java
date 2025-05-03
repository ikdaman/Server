package com.ikdaman.domain.mybook.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 500)
    public String impression;
    public String createdAt;

    @Builder
    public ImpressionReq(String impression, String createdAt) {
        this.impression = impression;
        this.createdAt = createdAt;
    }
}
