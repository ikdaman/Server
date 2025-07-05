package com.ikdaman.domain.notice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class Notice {
    @Id
    @GeneratedValue
    @Column(name = "notice_id", nullable = false, updatable = false)
    private int noticeId;

    @Column(length = 100)
    @Size(min = 1, max = 100)
    private String title;

    @Column(name = "notice_writer", length = 10)
    @Size(min = 1, max = 10)
    private String noticeWriter;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(updatable = false, name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Builder
    public Notice(String title, String noticeWriter, String content, LocalDateTime uploadedAt) {
        this.title = title;
        this.noticeWriter = noticeWriter;
        this.content = content;
        this.uploadedAt = uploadedAt;
    }
}