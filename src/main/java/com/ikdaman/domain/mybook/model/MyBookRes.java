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
    private int itemId;
    private int progressRate;
    private String impression;
    private String createdAt;

    @Builder
    public MyBookRes(int mybookId, String title, String writer, int itemId,
                     int progressRate, String impression, String createdAt) {
        this.mybookId = mybookId;
        this.title = title;
        this.writer = writer;
        this.itemId = itemId;
        this.progressRate = progressRate;
        this.impression = impression;
        this.createdAt = createdAt;
    }
}
