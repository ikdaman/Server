package com.ikdaman.domain.mybook.service;

import com.ikdaman.domain.bookLog.model.BookLogListRes;
import com.ikdaman.domain.mybook.model.*;
import com.ikdaman.global.auth.model.AuthMember;

import java.util.UUID;

/**
 * 나의 책 서비스
 */
public interface MyBookService {
    MyBookRes addMyBook(UUID memberId, MyBookReq dto);

    // 첫인상
    MyBookRes addImpression(UUID memberId, Integer myBookId, ImpressionReq dto);

    void deleteMyBook(UUID memberId, Integer mybookId);

    MyBookSearchRes searchMyBooks(MyBookSearchReq request, AuthMember authMember);

    InProgressBooksRes searchInProgressBooks(AuthMember authMember);

    MyBookDetailRes getMyBookDetail(UUID memberId, Long mybookId);

    BookLogListRes getMyBookLogs(UUID memberId, Long mybookId, Integer page, Integer limit);
}