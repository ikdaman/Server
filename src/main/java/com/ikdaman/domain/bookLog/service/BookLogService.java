package com.ikdaman.domain.bookLog.service;

import com.ikdaman.domain.bookLog.entity.BookLog;
import com.ikdaman.domain.bookLog.model.BookLogReq;
import com.ikdaman.domain.bookLog.model.CompletedReq;
import com.ikdaman.domain.bookLog.model.UpdateBookLogReq;
import com.ikdaman.domain.bookLog.model.UpdateCompletedReq;

public interface BookLogService {
    BookLog addBookLog(Long myBookId, BookLogReq request);

    BookLog updateBookLog(Long myBookId, Long bookLogId, UpdateBookLogReq request);

    void deleteBookLog(Long myBookId, Long bookLogId);

    // 완독
    BookLog addCompleted(Long myBookId, CompletedReq request);

    BookLog updateCompleted(Long myBookId, Long bookLogId, UpdateBookLogReq request);

    void deleteCompleted(Long myBookId, Long bookLogId);
}
