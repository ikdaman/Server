package com.ikdaman.domain.mybook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyBookDetailRes {
    private BookInfo bookInfo;
    private String mybookId;
    private String startDate;
    private int nowPage;
    private int progress;
    private String impression;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookInfo {
        private String itemId;
        private String title;
        private String author;
        private String coverImage;
        private String publisher;
        private int totalPage;
    }
}
