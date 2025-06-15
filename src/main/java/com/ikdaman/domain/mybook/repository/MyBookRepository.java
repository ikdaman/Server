package com.ikdaman.domain.mybook.repository;

import com.ikdaman.domain.mybook.entity.MyBook;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {

    @Query("""
        SELECT m FROM MyBook m
        LEFT JOIN m.book b
        LEFT JOIN b.author a
        LEFT JOIN a.writer w
        WHERE m.memberId = :memberId
        AND (
            :status IS NULL OR 
            (:status = 'completed' AND m.isReading = false) OR 
            (:status = 'in-progress' AND m.isReading = true)
        )
        AND (
            :keyword IS NULL OR 
            b.title LIKE :keyword OR 
            w.writerName LIKE :keyword
        )
    """)
    Page<MyBook> searchMyBooks(
            @Param("memberId") UUID memberId,
            @Param("status") String status,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query(value = """
        SELECT m FROM MyBook m
        LEFT JOIN m.book b
        LEFT JOIN b.author a
        LEFT JOIN a.writer w
        WHERE 
        (
            :status IS NULL OR 
            (:status = 'completed' AND m.isReading = false) OR 
            (:status = 'in-progress' AND m.isReading = true)
        )
        AND (
            :keyword is null OR
            b.title LIKE %:keyword% OR 
            w.writerName LIKE %:keyword%
        )
    """,
    countQuery = """
        SELECT COUNT(m) FROM MyBook m
        LEFT JOIN m.book b
        LEFT JOIN b.author a
        LEFT JOIN a.writer w
        WHERE 
        (
            :status IS NULL OR 
            (:status = 'completed' AND m.isReading = false) OR 
            (:status = 'in-progress' AND m.isReading = true)
        )
        AND (
            :keyword IS NULL OR
            b.title LIKE %:keyword% OR 
            w.writerName LIKE %:keyword%
        )
    """
    )
    Page<MyBook> searchMyBooksWithoutMemberId(
            @Param("status") String status,
            @Param("keyword") String keyword,
            Pageable pageable
    );


    @Query("""
    SELECT m FROM MyBook m
    LEFT JOIN m.book b
    LEFT JOIN m.bookLogs bl
    WHERE m.memberId = :memberId
        AND (bl.booklogType = 'IMPRESSION' OR bl.booklogType IS NULL)
        AND m.isReading = true
        AND m.status = 'ACTIVE'""")
    List<MyBook> findByMemberIdAndIsReading(UUID memberId, boolean isReading);

    @Query(value = """
        SELECT m FROM MyBook m
        LEFT JOIN m.book b
        LEFT JOIN m.bookLogs bl
        WHERE 
            m.isReading = true
            AND m.status = 'ACTIVE'
        """,
        countQuery = """
            SELECT COUNT(m) FROM MyBook m
            LEFT JOIN m.book b
            LEFT JOIN m.bookLogs bl
            WHERE 
                m.isReading = true
                AND m.status = 'ACTIVE'
        """
    )
    List<MyBook> findAllActiveReadingBooks();

}
