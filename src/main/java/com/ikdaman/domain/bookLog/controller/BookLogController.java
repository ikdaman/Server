package com.ikdaman.domain.bookLog.controller;

import com.ikdaman.domain.bookLog.model.BookLogReq;
import com.ikdaman.domain.bookLog.service.BookLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mybooks/{mybook_id}/booklog")
@RequiredArgsConstructor
public class BookLogController {

    private final BookLogService bookLogService;
    @PostMapping
    public ResponseEntity<?> addBooklog(
            @PathVariable("mybook_id") Long myBookId,
            @RequestBody BookLogReq request) {
        bookLogService.addBookLog(request);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{booklog_id}")
    public ResponseEntity<?> updateBooklog(
            @PathVariable("mybook_id") Long myBookId,
            @PathVariable("booklog_id") Long bookLogId,
            @RequestBody BookLogReq request) {
        bookLogService.updateBookLog(myBookId, bookLogId, request);
        return ResponseEntity.status(205).build();
    }

    @DeleteMapping("/{booklog_id}")
    public ResponseEntity<?> deleteBooklog(
            @PathVariable("mybook_id") Long myBookId,
            @PathVariable("booklog_id") Long bookLogId,
            @RequestBody BookLogReq request) {

        bookLogService.deleteBookLog(myBookId, bookLogId, request);
        return ResponseEntity.status(205).build();
    }
}
