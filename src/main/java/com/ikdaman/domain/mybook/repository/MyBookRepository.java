package com.ikdaman.domain.mybook.repository;

import com.ikdaman.domain.mybook.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {

}
