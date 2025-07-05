package com.ikdaman.domain.notice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class NoticeListRes {
    private List<NoticeListDTO> notices;
    private boolean hasNext;
    private Integer currentPage;
    private Integer totalPages;

    @Getter
    @AllArgsConstructor
    public static class NoticeListDTO {
        private int noticeId;
        private String title;
        private LocalDateTime uploadedAt;
    }
}
