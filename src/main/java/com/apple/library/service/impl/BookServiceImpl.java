package com.apple.library.service.impl;

import com.apple.library.entity.Book;
import com.apple.library.exception.DuplicateResourceException;
import com.apple.library.exception.ResourceNotFoundException;
import com.apple.library.repository.BookRepository;
import com.apple.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    public Page<Book> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public Book create(Book book) {
        if (repository.existsByIsbn(book.getIsbn())) {
            throw new DuplicateResourceException(
                    "Book with ISBN " + book.getIsbn() + " already exists"
            );
        }
        return repository.save(book);
    }

    public Book update(Long id, Book book) {
        Book existing = findById(id);
        book.setId(existing.getId());
        return repository.save(book);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
