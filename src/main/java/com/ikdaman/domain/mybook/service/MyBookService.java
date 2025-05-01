package com.ikdaman.domain.mybook.service;

import com.ikdaman.domain.mybook.model.MyBookReq;
import com.ikdaman.domain.mybook.model.MyBookRes;

/**
 * 나의 책 서비스
 */
public interface MyBookService {
    MyBookRes addMyBook(MyBookReq dto);
    // MyBookRes addMyBook(MyBookReq dto, String memberId);
}
