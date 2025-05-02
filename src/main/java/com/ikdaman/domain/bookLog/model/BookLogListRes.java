package com.ikdaman.domain.bookLog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookLogListRes {
    private List<BookLogDTO> booklogs;
    private boolean hasNext;

    @Getter
    @AllArgsConstructor
    public static class BookLogDTO {
        private Long booklogId;
        private String loggedDate;
        private Integer page;
        private String content;
        private String type; // 첫인상(IMPRESSION), 소감(REVIEW), 열음(OPEN), 생각(THINK)
    }
}
