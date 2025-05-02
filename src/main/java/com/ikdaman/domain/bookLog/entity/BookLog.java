package com.ikdaman.domain.bookLog.entity;

import com.ikdaman.domain.mybook.entity.MyBook;
import com.ikdaman.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Setter
public class BookLog extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long booklogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mybook_id", nullable = false)
    private MyBook myBook; // FK - 나의 책 ID

    @Column(length = 20, nullable = false)
    private String booklogType; // 기록 유형 (IMPRESSION, REVIEW, OPEN, THINK)

    private Integer page; // 읽은 페이지

    @Lob
    private String content; // 기록 내용

    @Builder
    public BookLog(MyBook myBook, String booklogType, Integer page, String content) {
        this.myBook = myBook;
        this.booklogType = booklogType;
        this.page = page;
        this.content = content;
    }
}
