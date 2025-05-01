package com.ikdaman.domain.book.entity;

import com.ikdaman.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@DynamicInsert
@DynamicUpdate
public class Book extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 100)
    private String publisher;

    @Column(nullable = false, length = 13)
    private String isbn;

    @Column(nullable = false)
    private int page;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "category_id")
    private int categoryId;

    @Builder
    public Book(int bookId, String title, String publisher, String isbn, int page, String coverImage, int categoryId) {
        this.bookId = bookId;
        this.title = title;
        this.publisher = publisher;
        this.isbn = isbn;
        this.page = page;
        this.coverImage = coverImage;
        this.categoryId = categoryId;
    }
}
