package com.ikdaman.domain.mybook.entity;

import com.ikdaman.domain.book.entity.Author;
import com.ikdaman.domain.book.entity.Book;
import com.ikdaman.domain.bookLog.entity.BookLog;
import com.ikdaman.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@DynamicInsert
@DynamicUpdate
public class MyBook extends BaseTime {
    @Id
    @GeneratedValue
    @Column(name = "mybook_id", nullable = false, updatable = false)
    private int mybookId;

    @Column(name = "member_id")
    private UUID memberId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "now_page")
    @Builder.Default
    private int nowPage = 0;

    @Column(name = "is_reading")
    @Builder.Default
    private Boolean isReading = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private MyBook.Status status = MyBook.Status.ACTIVE; // 기본값 ACTIVE

    @OneToMany(mappedBy = "myBook", fetch = FetchType.LAZY)
    private List<BookLog> bookLogs; // FK - 책 로그 ID

    // 계정 상태 ENUM
    public enum Status {
        ACTIVE, INACTIVE
    }

    public void updateToInactive() {
        this.status = Status.INACTIVE;
    }

    @Builder
    public MyBook(int mybookId, UUID memberId, Book book, int nowPage, Boolean isReading, MyBook.Status status, List<BookLog> bookLogs) {
        this.mybookId = mybookId;
        this.memberId = memberId;
        this.book =  book;
        this.nowPage = nowPage;
        this.isReading = isReading;
        this.status = status;
        this.bookLogs = bookLogs;
    }
}
