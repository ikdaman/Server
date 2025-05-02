package com.ikdaman.domain.mybook.controller;

import com.ikdaman.domain.mybook.model.*;
import com.ikdaman.domain.mybook.service.MyBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mybooks")
@RequiredArgsConstructor
public class MyBookController {

    private final MyBookService myBookService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyBookRes> addMyBook(
            @RequestBody MyBookReq dto
            // @RequestHeader("nickname") String nickname // 임시 인증 방식
    ) {
        MyBookRes myBookRes = myBookService.addMyBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{mybook_id}")
    public ResponseEntity<Void> deleteMyBook(@PathVariable Integer mybook_id) {
        myBookService.deleteMyBook(mybook_id);
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build();
    }

    @GetMapping()
    public MyBookSearchRes searchMyBooks(@ModelAttribute MyBookSearchReq request) {
        return myBookService.searchMyBooks(request);
    }

    // 독서중인 책 목록 조회
    @GetMapping("/in-progress")
    public InProgressBooksRes searchInProgressBooks() {
        return myBookService.searchInProgressBooks();
    }

}