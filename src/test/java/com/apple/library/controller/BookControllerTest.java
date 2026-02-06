//package com.apple.library.controller;
//
//import com.apple.library.config.TestSecurityConfig;
//import com.apple.library.entity.Book;
//import com.apple.library.entity.Genre;
//import com.apple.library.service.BookService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(BookController.class)
//@AutoConfigureMockMvc(addFilters = false) // disable Spring Security for controller tests
//class BookControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockitoBean
//    private BookService bookService;
//
//    // ------------------- GET ALL -------------------
//
//    @Test
//    @DisplayName("Should return paginated list of books")
//    void shouldGetAllBooks() throws Exception {
//        Book book = validBook();
//        Page<Book> page = new PageImpl<>(List.of(book));
//
//        when(bookService.findAll(any(Pageable.class))).thenReturn(page);
//
//        mockMvc.perform(get("/api/books")
//                        .param("page", "0")
//                        .param("size", "10"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].title").value("Clean Code"));
//    }
//
//    // ------------------- GET BY ID -------------------
//
//    @Test
//    @DisplayName("Should return book by id")
//    void shouldGetBookById() throws Exception {
//        when(bookService.findById(1L)).thenReturn(validBook());
//
//        mockMvc.perform(get("/api/books/{id}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("Clean Code"));
//    }
//
//    // ------------------- CREATE -------------------
//
//    @Test
//    @DisplayName("Should create book successfully")
//    void shouldCreateBook() throws Exception {
//        Book savedBook = validBook();
//        savedBook.setId(1L);
//
//        when(bookService.create(any(Book.class))).thenReturn(savedBook);
//
//        mockMvc.perform(post("/api/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(validBook())))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.title").value("Clean Code"));
//    }
//
//    // ------------------- UPDATE -------------------
//
//    @Test
//    @DisplayName("Should update book successfully")
//    void shouldUpdateBook() throws Exception {
//        Book updatedBook = validBook();
//        updatedBook.setTitle("Clean Architecture");
//
//        when(bookService.update(eq(1L), any(Book.class)))
//                .thenReturn(updatedBook);
//
//        mockMvc.perform(put("/api/books/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedBook)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("Clean Architecture"));
//    }
//
//    // ------------------- DELETE -------------------
//
//    @Test
//    @DisplayName("Should delete book successfully")
//    void shouldDeleteBook() throws Exception {
//        doNothing().when(bookService).delete(1L);
//
//        mockMvc.perform(delete("/api/books/{id}", 1L))
//                .andExpect(status().isNoContent());
//    }
//
//    // ------------------- VALIDATION -------------------
//
//    @Test
//    @DisplayName("Should return 400 when validation fails")
//    void shouldFailValidation() throws Exception {
//        Book invalidBook = new Book();
//        invalidBook.setTitle(""); // invalid
//        invalidBook.setAuthor(""); // invalid
//
//        mockMvc.perform(post("/api/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidBook)))
//                .andExpect(status().isBadRequest());
//    }
//
//    // ------------------- HELPER -------------------
//
//    private Book validBook() {
//        Book book = new Book();
//        book.setTitle("Clean Code");
//        book.setAuthor("Robert C. Martin");
//        book.setPublicationYear(2008);
//        return book;
//    }
//}
