package com.ikdaman.domain.book.repository;


import com.ikdaman.domain.book.entity.Author;
import com.ikdaman.domain.book.entity.Book;
import com.ikdaman.domain.book.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByBookAndWriter(Book book, Writer writer);
    Optional<Author> findByBook(Book book);
}
