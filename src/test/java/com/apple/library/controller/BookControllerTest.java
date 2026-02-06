package com.apple.library.controller;

import com.apple.library.entity.Book;
import com.apple.library.entity.Genre;
import com.apple.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("local")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private BookService bookService;

    private Book validBook() {
        return new Book(
                1L,
                "Effective Java",
                "Joshua Bloch",
                "9780134685991",
                2018,
                Genre.SCIENCE,
                5,
                10
        );
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        Page<Book> page = new PageImpl<>(List.of(validBook()));

        Mockito.when(bookService.findAll(any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title")
                        .value("Effective Java"));
    }


    @Test
    void getById_shouldReturn200() throws Exception {
        Mockito.when(bookService.findById(1L))
                .thenReturn(validBook());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Joshua Bloch"));
    }

    @Test
    void create_shouldReturn201() throws Exception {
        Book book = validBook();
        book.setId(null); // simulate create

        Mockito.when(bookService.create(Mockito.any(Book.class)))
                .thenReturn(validBook());

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("9780134685991"));
    }

    @Test
    void update_shouldReturn200() throws Exception {
        Mockito.when(bookService.update(Mockito.eq(1L), Mockito.any(Book.class)))
                .thenReturn(validBook());

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBook())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        Mockito.doNothing().when(bookService).delete(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }
}