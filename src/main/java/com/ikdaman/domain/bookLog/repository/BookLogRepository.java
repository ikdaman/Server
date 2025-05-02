package com.ikdaman.domain.bookLog.repository;

import com.ikdaman.domain.bookLog.entity.BookLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLogRepository extends JpaRepository<BookLog, Long> {
}
