package com.apple.library.service;

import com.apple.library.entity.Book;
import com.apple.library.exception.DuplicateResourceException;
import com.apple.library.exception.ResourceNotFoundException;
import com.apple.library.repository.BookRepository;
import com.apple.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookServiceImpl service;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setIsbn("9780134685991");
        book.setPublicationYear(2018);
        book.setTotalCopies(10);
        book.setAvailableCopies(5);
    }

    // ---------------- findAll ----------------

    @Test
    @DisplayName("findAll should return paged books")
    void findAllShouldReturnBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> page = new PageImpl<>(List.of(book));

        when(repository.findAll(pageable)).thenReturn(page);

        Page<Book> result = service.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        verify(repository).findAll(pageable);
    }

    // ---------------- findById ----------------

    @Test
    @DisplayName("findById should return book when found")
    void findByIdShouldReturnBook() {
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        Book result = service.findById(1L);

        assertEquals(book.getId(), result.getId());
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("findById should throw exception when not found")
    void findByIdShouldThrowWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(1L)
        );

        assertEquals("Book not found", ex.getMessage());
        verify(repository).findById(1L);
    }

    // ---------------- create ----------------

    @Test
    @DisplayName("create should save book when ISBN is unique")
    void createShouldSaveBook() {
        when(repository.existsByIsbn(book.getIsbn())).thenReturn(false);
        when(repository.save(book)).thenReturn(book);

        Book result = service.create(book);

        assertEquals(book.getIsbn(), result.getIsbn());
        verify(repository).existsByIsbn(book.getIsbn());
        verify(repository).save(book);
    }

    @Test
    @DisplayName("create should throw exception when ISBN already exists")
    void createShouldThrowWhenDuplicateIsbn() {
        when(repository.existsByIsbn(book.getIsbn())).thenReturn(true);

        DuplicateResourceException ex = assertThrows(
                DuplicateResourceException.class,
                () -> service.create(book)
        );

        assertEquals(
                "Book with ISBN " + book.getIsbn() + " already exists",
                ex.getMessage()
        );

        verify(repository).existsByIsbn(book.getIsbn());
        verify(repository, never()).save(any());
    }

    // ---------------- update ----------------

    @Test
    @DisplayName("update should modify existing book")
    void updateShouldSaveBook() {
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(any(Book.class))).thenReturn(book);

        Book updated = new Book();
        updated.setTitle("Updated Title");

        Book result = service.update(1L, updated);

        assertEquals(1L, result.getId());
        verify(repository).findById(1L);
        verify(repository).save(updated);
    }

    // ---------------- delete ----------------

    @Test
    @DisplayName("delete should remove existing book")
    void deleteShouldRemoveBook() {
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(repository).delete(book);

        service.delete(1L);

        verify(repository).findById(1L);
        verify(repository).delete(book);
    }
}
