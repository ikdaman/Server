package com.ikdaman.domain.notice.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeReq {
    private String title;
    private String noticeWriter;
    private String content;

    @Builder
    public NoticeReq(String title, String noticeWriter, String content) {
        this.title = title;
        this.noticeWriter = noticeWriter;
        this.content = content;
    }
}
