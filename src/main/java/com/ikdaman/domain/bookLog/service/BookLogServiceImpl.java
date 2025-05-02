package com.ikdaman.domain.bookLog.service;

import com.ikdaman.domain.bookLog.entity.BookLog;
import com.ikdaman.domain.bookLog.model.*;
import com.ikdaman.domain.bookLog.repository.BookLogRepository;
import com.ikdaman.domain.mybook.entity.MyBook;
import com.ikdaman.domain.mybook.repository.MyBookRepository;
import com.ikdaman.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ikdaman.global.exception.ErrorCode.NOT_FOUND_MY_BOOK;

@Service
@RequiredArgsConstructor
public class BookLogServiceImpl implements BookLogService {

    private final BookLogRepository bookLogRepository;
    private final MyBookRepository myBookRepository;

    @Override
    @Transactional
    public BookLog addBookLog(Long myBookId, BookLogReq request) {
        MyBook myBook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_MY_BOOK));

        BookLog bookLog = BookLog.builder()
                .myBook(myBook)
                .page(request.getPage())
                .content(request.getContent())
                .booklogType(BookLogType.THINK.name())
                .build();

        return bookLogRepository.save(bookLog);
    }

    @Override
    @Transactional
    public BookLog updateBookLog(Long myBookId, Long bookLogId, UpdateBookLogReq request) {
        BookLog bookLog = bookLogRepository.findById(bookLogId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_MY_BOOK));

        bookLog.setContent(request.getContent());
        return bookLogRepository.save(bookLog);
    }

    @Override
    public void deleteBookLog(Long myBookId, Long bookLogId) {
        BookLog bookLog = bookLogRepository.findById(bookLogId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_MY_BOOK));

        bookLogRepository.delete(bookLog);
    }

    // 완독
    @Override
    public BookLog addCompleted(Long myBookId, CompletedReq request) {
        MyBook myBook = myBookRepository.findById(myBookId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_MY_BOOK));

        BookLog bookLog = BookLog.builder()
                .myBook(myBook)
                .page(myBook.getBook().getPage())
                .content(request.getReview())
                .booklogType(BookLogType.REVIEW.name())
                .build();

        return bookLogRepository.save(bookLog);
    }

    @Override
    public BookLog updateCompleted(Long myBookId, Long bookLogId, UpdateBookLogReq request) {
        BookLog bookLog = bookLogRepository.findById(bookLogId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_MY_BOOK));

        bookLog.setContent(request.getContent());
        return bookLogRepository.save(bookLog);
    }

    @Override
    public void deleteCompleted(Long myBookId, Long bookLogId) {
        BookLog bookLog = bookLogRepository.findById(bookLogId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_MY_BOOK));

        bookLogRepository.delete(bookLog);
    }
}
