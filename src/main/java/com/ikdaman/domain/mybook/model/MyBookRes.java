package com.ikdaman.domain.mybook.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyBookRes {
    private int mybookId;
    private String title;
    private String writer;
    private int page;
    private String impression;
    private String createdAt;

    @Builder
    public MyBookRes(int mybookId, String title, String writer, int page, String impression, String createdAt) {
        this.mybookId = mybookId;
        this.title = title;
        this.writer = writer;
        this.page = page;
        this.impression = impression;
        this.createdAt = createdAt;
    }
}
