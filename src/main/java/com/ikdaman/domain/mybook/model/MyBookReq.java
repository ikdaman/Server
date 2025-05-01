package com.ikdaman.domain.mybook.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 나의 책 추가 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyBookReq {
    private String title;
    private String writer;
    private String publisher;
    @Pattern(
            regexp = "^(?:97[89])?\\d{9}[\\dXx]$",
            message = "유효한 ISBN 형식이 아닙니다."
    )
    private String isbn;
    private int page;
    private String coverImage;
    @Size(max=50)
    private String impression;
    private String createdAt;

    @Builder
    public MyBookReq(String title, String writer, String publisher, String isbn,
                     int page, String coverImage, String impression, String createdAt) {
        this.title = title;
        this.writer = writer;
        this.publisher = publisher;
        this.isbn = isbn;
        this.page = page;
        this.coverImage = coverImage;
        this.impression = impression;
        this.createdAt = createdAt;
    }
}
