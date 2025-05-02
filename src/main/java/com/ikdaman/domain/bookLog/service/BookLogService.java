package com.ikdaman.domain.bookLog.service;

import com.ikdaman.domain.bookLog.entity.BookLog;
import com.ikdaman.domain.bookLog.model.BookLogReq;

public interface BookLogService {
    BookLog addBookLog(BookLogReq request);

    BookLog updateBookLog(Long myBookId, Long bookLogId, BookLogReq request);

    BookLog deleteBookLog(Long myBookId, Long bookLogId, BookLogReq request);
}
