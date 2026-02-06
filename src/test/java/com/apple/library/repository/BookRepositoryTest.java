package com.apple.library.repository;

import com.apple.library.entity.Book;
import com.apple.library.entity.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    @DisplayName("existsByIsbn should return true when ISBN exists")
    void existsByIsbnShouldReturnTrue() {
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setIsbn("9780134685991");
        book.setPublicationYear(2018);
        book.setGenre(Genre.SCIENCE);
        book.setTotalCopies(10);
        book.setAvailableCopies(5);

        repository.save(book);

        boolean exists = repository.existsByIsbn("9780134685991");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("existsByIsbn should return false when ISBN does not exist")
    void existsByIsbnShouldReturnFalse() {
        boolean exists = repository.existsByIsbn("9999999999999");

        assertThat(exists).isFalse();
    }
}
