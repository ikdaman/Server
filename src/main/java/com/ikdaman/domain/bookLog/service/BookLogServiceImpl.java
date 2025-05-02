package com.ikdaman.domain.bookLog.service;

import com.ikdaman.domain.bookLog.entity.BookLog;
import com.ikdaman.domain.bookLog.model.BookLogReq;
import com.ikdaman.domain.bookLog.repository.BookLogRepository;
import com.ikdaman.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.ikdaman.global.exception.ErrorCode.NOT_FOUND_MY_BOOK;

@Service
@RequiredArgsConstructor
public class BookLogServiceImpl implements BookLogService {

    private final BookLogRepository myBookRepository;

    @Override
    @Transactional
    public BookLog addBookLog(BookLogReq request) {
        BookLog bookLog = BookLog.builder()
                .page(request.getPage())
                .content(request.getContent())
                .build();

        return myBookRepository.save(bookLog);
    }

    @Override
    @Transactional
    public BookLog updateBookLog(Long myBookId, Long bookLogId, BookLogReq request) {
        // Fetch the existing BookLog entity by its ID
        BookLog bookLog = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_MY_BOOK));

        // Update the content and updatedAt fields
        bookLog.setContent(request.getContent());

        // Save the updated entity
        return myBookRepository.save(bookLog);
    }

    @Override
    public BookLog deleteBookLog(Long myBookId, Long bookLogId, BookLogReq request) {
        return null;
    }
}
