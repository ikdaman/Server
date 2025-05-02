package com.ikdaman.domain.mybook.service;

import com.ikdaman.domain.mybook.model.*;

/**
 * 나의 책 서비스
 */
public interface MyBookService {
    MyBookRes addMyBook(MyBookReq dto);
    // MyBookRes addMyBook(MyBookReq dto, String memberId);
    void deleteMyBook(Integer mybookId);

    MyBookSearchRes searchMyBooks(MyBookSearchReq request);

    InProgressBooksRes searchInProgressBooks();

    MyBookDetailRes searchMyBookDetail(Integer mybookId);
}