package com.ikdaman.domain.mybook.service;

import com.ikdaman.domain.mybook.model.*;

/**
 * 나의 책 서비스
 */
public interface MyBookService {
    MyBookRes addMyBook(MyBookReq dto);
    // MyBookRes addMyBook(MyBookReq dto, String memberId);

    // 첫인상
    MyBookRes addImpression(Integer myBookId, ImpressionReq dto);

    void deleteMyBook(Integer mybookId);

    MyBookSearchRes searchMyBooks(MyBookSearchReq request);

    InProgressBooksRes searchInProgressBooks();
}