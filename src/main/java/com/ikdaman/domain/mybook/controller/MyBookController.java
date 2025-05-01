package com.ikdaman.domain.mybook.controller;

import com.ikdaman.domain.mybook.model.MyBookReq;;
import com.ikdaman.domain.mybook.model.MyBookRes;
import com.ikdaman.domain.mybook.service.MyBookService;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(myBookRes);
    }
}

