package com.ikdaman.domain.bookLog.controller;

import com.ikdaman.domain.bookLog.model.BookLogReq;
import com.ikdaman.domain.bookLog.model.CompletedReq;
import com.ikdaman.domain.bookLog.model.UpdateBookLogReq;
import com.ikdaman.domain.bookLog.model.UpdateCompletedReq;
import com.ikdaman.domain.bookLog.service.BookLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mybooks/{mybook_id}")
@RequiredArgsConstructor
public class BookLogController {

    private final BookLogService bookLogService;
    @PostMapping("/booklog")
    public ResponseEntity<?> addBooklog(
            @PathVariable("mybook_id") Long myBookId,
            @RequestBody BookLogReq request) {
        bookLogService.addBookLog(myBookId, request);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/booklog/{booklog_id}")
    public ResponseEntity<?> updateBooklog(
            @PathVariable("mybook_id") Long myBookId,
            @PathVariable("booklog_id") Long bookLogId,
            @RequestBody UpdateBookLogReq request) {
        bookLogService.updateBookLog(myBookId, bookLogId, request);
        return ResponseEntity.status(205).build();
    }

    @DeleteMapping("/booklog/{booklog_id}")
    public ResponseEntity<?> deleteBooklog(
            @PathVariable("mybook_id") Long myBookId,
            @PathVariable("booklog_id") Long bookLogId) {

        bookLogService.deleteBookLog(myBookId, bookLogId);
        return ResponseEntity.status(205).build();
    }

    // 완독
    @PostMapping("/completed")
    public ResponseEntity<?> addCompleted(
            @PathVariable("mybook_id") Long myBookId,
            @RequestBody CompletedReq request) {
        bookLogService.addCompleted(myBookId, request);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/booklog/{booklog_id}/completed")
    public ResponseEntity<?> updateCompleted(
            @PathVariable("mybook_id") Long myBookId,
            @PathVariable("booklog_id") Long bookLogId,
            @RequestBody UpdateBookLogReq request) {
        bookLogService.updateCompleted(myBookId, bookLogId, request);
        return ResponseEntity.status(205).build();
    }

    @DeleteMapping("/booklog/{booklog_id}/completed")
    public ResponseEntity<?> deleteCompleted(
            @PathVariable("mybook_id") Long myBookId,
            @PathVariable("booklog_id") Long bookLogId) {

        bookLogService.deleteCompleted(myBookId, bookLogId);
        return ResponseEntity.status(205).build();
    }
}
