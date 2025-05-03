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
public class Writer extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writer_id")
    private int writerId;

    @Column(name = "writer_name", nullable = false, length = 100)
    private String writerName;

    @Builder
    public Writer(int writerId, String writerName) {
        this.writerId = writerId;
        this.writerName = writerName;
    }
}