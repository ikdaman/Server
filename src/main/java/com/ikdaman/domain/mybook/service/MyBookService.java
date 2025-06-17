package com.ikdaman.domain.mybook.service;

import com.ikdaman.domain.bookLog.model.BookLogListRes;
import com.ikdaman.domain.mybook.model.*;
import com.ikdaman.global.auth.model.AuthMember;

import java.util.UUID;

/**
 * 나의 책 서비스
 */
public interface MyBookService {
    MyBookRes addMyBook(MyBookReq dto);
    // MyBookRes addMyBook(MyBookReq dto, String memberId);

    // 첫인상
    MyBookRes addImpression(Integer myBookId, ImpressionReq dto);

    void deleteMyBook(Integer mybookId);

    MyBookSearchRes searchMyBooks(MyBookSearchReq request, AuthMember authMember);

    InProgressBooksRes searchInProgressBooks(AuthMember authMember);

    MyBookDetailRes getMyBookDetail(UUID memberId, Long mybookId);

    BookLogListRes getMyBookLogs(UUID memberId, Long mybookId, Integer page, Integer limit);
}