package com.ikdaman.domain.mybook.entity;

import com.ikdaman.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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

    @Column(name = "book_id", nullable = false, updatable = false)
    private int bookId;

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

    // 계정 상태 ENUM
    public enum Status {
        ACTIVE, INACTIVE
    }

    @Builder
    public MyBook(int mybookId, UUID memberId, int bookId, int nowPage, Boolean isReading, MyBook.Status status) {
        this.mybookId = mybookId;
        this.memberId = memberId;
        this.bookId =  bookId;
        this.nowPage = nowPage;
        this.isReading = isReading;
        this.status = status;
    }
}
