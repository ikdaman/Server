package com.ikdaman.domain.mybook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class InProgressBooksRes {
    private List<BookDto> books;

    public InProgressBooksRes() {}

    @Data
    @Builder
    @AllArgsConstructor
    public static class BookDto {
        private int mybookId;
        private String title;
        private String author;
        private String coverImage;
        private String progress;
        private String firstImpression;
        private String recentEdit;
    }
}
