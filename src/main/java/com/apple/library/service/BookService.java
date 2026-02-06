package com.apple.library.service;

import com.apple.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<Book> findAll(Pageable pageable);
    Book findById(Long id);
    Book create(Book book);
    Book update(Long id, Book book);
    void delete(Long id);
}
