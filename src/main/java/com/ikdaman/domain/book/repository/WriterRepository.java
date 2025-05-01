package com.ikdaman.domain.book.repository;

import com.ikdaman.domain.book.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WriterRepository extends JpaRepository<Writer, Long> {
    Optional<Writer> findByWriterName(String writerName);
}
