package com.ikdaman.domain.mybook.service;

import com.ikdaman.domain.member.entity.Member;
import com.ikdaman.domain.member.repository.MemberRepository;
import com.ikdaman.domain.book.entity.Author;
import com.ikdaman.domain.book.entity.Book;
import com.ikdaman.domain.mybook.entity.MyBook;
import com.ikdaman.domain.book.entity.Writer;
import com.ikdaman.domain.mybook.model.MyBookReq;
import com.ikdaman.domain.mybook.model.MyBookRes;
import com.ikdaman.domain.book.repository.AuthorRepository;
import com.ikdaman.domain.book.repository.BookRepository;
import com.ikdaman.domain.mybook.repository.MyBookRepository;
import com.ikdaman.domain.book.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .bookId(book.getBookId())
                .nowPage(0)
                .isReading(true)
                .build();

        myBookRepository.save(myBook);

        return MyBookRes.builder()
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .page(dto.getPage())
                .impression(dto.getImpression())
                .createdAt(dto.getCreatedAt())
                .build();
    }

}
