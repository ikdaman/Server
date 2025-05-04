package com.ikdaman.domain.book.repository;

import com.ikdaman.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    // boolean existsMyBookByMemberIdAndBook(UUID memberId, Book Book);
}
