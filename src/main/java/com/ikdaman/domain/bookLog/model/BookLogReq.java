package com.ikdaman.domain.bookLog.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 생각 추가 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookLogReq {
    private Integer page;
    private String content;
    private String createdAt;
}
