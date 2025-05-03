package com.ikdaman.domain.mybook.service;

import com.ikdaman.domain.bookLog.entity.BookLog;
import com.ikdaman.domain.bookLog.model.BookLogType;
import com.ikdaman.domain.bookLog.repository.BookLogRepository;
import com.ikdaman.domain.bookLog.model.BookLogListRes;
import com.ikdaman.domain.member.entity.Member;
import com.ikdaman.domain.member.repository.MemberRepository;
import com.ikdaman.domain.bookLog.repository.BookLogRepository;
import com.ikdaman.domain.book.entity.Author;
import com.ikdaman.domain.book.entity.Book;
import com.ikdaman.domain.bookLog.entity.BookLog;
import com.ikdaman.domain.mybook.entity.MyBook;
import com.ikdaman.domain.book.entity.Writer;
import com.ikdaman.domain.mybook.model.*;
import com.ikdaman.domain.book.repository.AuthorRepository;
import com.ikdaman.domain.book.repository.BookRepository;
import com.ikdaman.domain.mybook.repository.MyBookRepository;
import com.ikdaman.domain.book.repository.WriterRepository;
import com.ikdaman.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ikdaman.global.exception.ErrorCode.*;
import static com.ikdaman.global.util.BookProgress.calculateProgress;

/**
 * 나의 책 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class MyBookServiceImpl implements MyBookService {

    private final MyBookRepository myBookRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final WriterRepository writerRepository;
    private final BookLogRepository bookLogRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MyBookRes addMyBook(MyBookReq dto) {
        Writer writer = writerRepository.findByWriterName(dto.getWriter())
                .orElseGet(() -> writerRepository.save(
                        Writer.builder()
                                .writerName(dto.getWriter())
                                .build()
                ));

        Book book = bookRepository.findByIsbn(dto.getIsbn())
                .orElseGet(() -> {
                    Book newBook = Book.builder()
                            .title(dto.getTitle())
                            .publisher(dto.getPublisher())
                            .isbn(dto.getIsbn())
                            .page(dto.getPage())
                            .coverImage(dto.getCoverImage())
                            .build();

                    return bookRepository.save(newBook);
                });

        if (!authorRepository.existsByBookAndWriter(book, writer)) {
            authorRepository.save(Author.builder()
                    .book(book)
                    .writer(writer)
                    .build());
        }

//        Member member = memberRepository.findByNickname(nickname)
//                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        MyBook myBook = MyBook.builder()
//                .memberId(member.getMemberId())
                .book(book)
                .nowPage(0)
                .isReading(true)
                .build();

        myBookRepository.save(myBook);

        return MyBookRes.builder()
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .progressRate("0")
                .impression(dto.getImpression())
                .createdAt(dto.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public MyBookRes addImpression(Integer myBookId, ImpressionReq dto) {
        MyBook myBook = myBookRepository.findById(Long.valueOf(myBookId))
                .orElseThrow(() -> new BaseException(NOT_FOUND_MY_BOOK));

        Book book = bookRepository.findById(Long.valueOf(myBook.getBook().getBookId()))
                .orElseThrow(() -> new BaseException(NOT_FOUND_BOOK));

        // Author를 통해 writer_name 조회
        Author author = authorRepository.findByBook(book)
                .orElseThrow(() -> new BaseException(NOT_FOUND_AUTHOR));

        String writerName = author.getWriter().getWriterName();

        if(dto.getImpression().isEmpty()) {
            throw new BaseException(EMPTY_IMPRESSION);
        }

        BookLog bookLog = BookLog.builder()
                .myBook(myBook)
                .page(0)
                .content(dto.getImpression())
                .booklogType(BookLogType.IMPRESSION.name())
                .build();

        bookLogRepository.save(bookLog);

        return MyBookRes.builder()
                .mybookId(myBookId)
                .title(book.getTitle())
                .writer(writerName)
                .progressRate(String.format("%.2f", (double) myBook.getNowPage() / book.getPage() * 100))
                .impression(dto.getImpression())
                .createdAt(String.valueOf(myBook.getCreatedAt()))
                .build();
    }

    @Override
    public MyBookSearchRes searchMyBooks(MyBookSearchReq request) {
        int page = request.getPage() - 1; // PageRequest는 0부터 시작
        int limit = request.getLimit() + 1;

        PageRequest pageRequest = PageRequest.of(page, limit);
        //UUID memberId = request.getMemberId();
        UUID memberId = UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851");

        String keyword = request.getKeyword();
        String safeKeyword = keyword == null ? null : "%" + keyword + "%";

        Page<MyBook> resultPage = myBookRepository.searchMyBooks(
                memberId,
                safeKeyword,
                keyword,
                pageRequest
        );

        List<MyBookSearchRes.BookDto> books = resultPage.getContent().stream()
                .map(myBook -> {
                    Book book = myBook.getBook();
                    String authorNames = book.getAuthor().stream()
                            .map(author -> author.getWriter().getWriterName())
                            .collect(Collectors.joining(", ")); // 여러 작가면 ,로 구분

                    return MyBookSearchRes.BookDto.builder()
                            .mybookId(myBook.getMybookId())
                            .title(book.getTitle())
                            .author(authorNames)
                            .coverImage(book.getCoverImage())
                            .build();
                })
                .toList();

        return MyBookSearchRes.builder()
                .books(books)
                .nowPage(resultPage.getNumber() + 1)
                .totalPage(resultPage.getTotalPages())
                .build();
    }

    @Override
    public InProgressBooksRes searchInProgressBooks() {
        //UUID memberId = request.getMemberId();
        UUID memberId = UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851");

        List<MyBook> myBooks = myBookRepository.findByMemberIdAndIsReading(memberId, true);

        List<InProgressBooksRes.BookDto> bookDtos = myBooks.stream()
                .map(myBook -> {
                    Book book = myBook.getBook();
                    String authorNames = book.getAuthor().stream()
                            .map(author -> author.getWriter().getWriterName())
                            .collect(Collectors.joining(", ")); // 여러 작가면 ,로 구분

                    return InProgressBooksRes.BookDto.builder()
                            .mybookId(myBook.getMybookId())
                            .title(book.getTitle())
                            .author(authorNames)
                            .coverImage(book.getCoverImage())
                            .progress(String.format("%.2f", (double) myBook.getNowPage() / book.getPage() * 100) + "%")
                            .firstImpression(myBook.getBookLogs().isEmpty() ? null : myBook.getBookLogs().get(0).getContent())
                            .recentEdit(String.valueOf(myBook.getUpdatedAt()))
                            .build();
                })
                .collect(Collectors.toList());

        return InProgressBooksRes.builder()
                .books(bookDtos)
                .build();
    }

    // 나의 책 정보 조회
    @Override
    @Transactional(readOnly = true)
    public MyBookDetailRes getMyBookDetail(Long mybookId) {
        MyBook myBook = myBookRepository.findById(mybookId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_BOOK));
        Book book = myBook.getBook();

        // 작가열 생성
        List<Author> authors = book.getAuthor();
        String authorNames = authors.stream()
                .map(a -> a.getWriter().getWriterName())
                .collect(Collectors.joining(", "));

        // 첫인상 추가
        String impression = bookLogRepository.findFirstByMyBookAndBooklogType(myBook, "IMPRESSION")
                .map(BookLog::getContent)
                .orElse(null);

        // 책 정보 추가
        MyBookDetailRes.BookInfo bookInfo = MyBookDetailRes.BookInfo.builder()
                .title(book.getTitle())
                .author(authorNames)
                .coverImage(book.getCoverImage())
                .publisher(book.getPublisher())
                .totalPage(book.getPage())
                .build();

        // 나의책 정보 추가
        return MyBookDetailRes.builder()
                .bookInfo(bookInfo)
                .mybookId(String.valueOf(myBook.getMybookId()))
                .startDate(myBook.getCreatedAt().toString())
                .nowPage(myBook.getNowPage())
                .progress(calculateProgress(myBook.getNowPage(), book.getPage()))
                .impression(impression)
                .build();
    }

    // 나의 책 기록 조회
    @Override
    @Transactional(readOnly = true)
    public BookLogListRes getMyBookLogs(Long mybookId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<BookLog> resultPage = bookLogRepository.findByMyBook_MybookId(mybookId, pageable);

        List<BookLogListRes.BookLogDTO> booklogs = resultPage.getContent().stream()
                .map(log -> new BookLogListRes.BookLogDTO(
                        log.getBooklogId(),
                        log.getCreatedAt().toString(),
                        log.getPage(),
                        log.getContent(),
                        log.getBooklogType()
                )).toList();

        return new BookLogListRes(booklogs, resultPage.hasNext());
    }

    @Override
    @Transactional
    public void deleteMyBook(Integer id) {
        MyBook myBook = myBookRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new BaseException(NOT_FOUND_MY_BOOK));

        myBook.updateToInactive();
        myBookRepository.save(myBook);
    }
}