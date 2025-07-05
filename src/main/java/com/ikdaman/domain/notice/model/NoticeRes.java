package com.ikdaman.domain.notice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeRes {
    private int noticeId;
    private String title;
    private String content;
    private LocalDateTime uploadedAt;
    private String noticeWriter;

    @Builder
    public NoticeRes(int noticeId, String title, String content, LocalDateTime uploadedAt, String noticeWriter) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.uploadedAt = uploadedAt;
        this.noticeWriter = noticeWriter;
    }
}
