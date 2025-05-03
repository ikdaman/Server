package com.ikdaman.domain.bookLog.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompletedReq {
    private String review;
    private String createdAt;
}
