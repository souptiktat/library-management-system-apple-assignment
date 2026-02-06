//package com.apple.library.controller;
//
//import com.apple.library.config.TestSecurityConfig;
//import com.apple.library.entity.Book;
//import com.apple.library.entity.Genre;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Import(TestSecurityConfig.class)
//public class BookValidationParameterizedTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private Book baseValidBook() {
//        Book book = new Book();
//        book.setTitle("Test Book");
//        book.setAuthor("Valid Author");
//        book.setIsbn("9780132350884");
//        book.setPublicationYear(2020);
//        book.setGenre(Genre.FICTION);
//        book.setAvailableCopies(1);
//        book.setTotalCopies(2);
//        return book;
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "'',Valid Author",
//            "A,Valid Author",
//            "This title is intentionally made extremely long to exceed the maximum allowed length of two hundred characters which should trigger validation failure in the application layer,Valid Author"
//    })
//    void titleValidation_shouldFail(String title, String author) throws Exception {
//        Book book = baseValidBook();
//        book.setTitle(title);
//        book.setAuthor(author);
//        mockMvc.perform(post("/api/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(book)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "A",
//            "",
//            "123"
//    })
//    void authorValidation_shouldFail(String author) throws Exception {
//        Book book = baseValidBook();
//        book.setAuthor(author);
//        mockMvc.perform(post("/api/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(book)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "123",
//            "ISBN123456",
//            "978123"
//    })
//    void isbnValidation_shouldFail(String isbn) throws Exception {
//        Book book = baseValidBook();
//        book.setIsbn(isbn);
//        mockMvc.perform(post("/api/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(book)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "1400",
//            "3000"
//    })
//    void publicationYearValidation_shouldFail(int year) throws Exception {
//        Book book = baseValidBook();
//        book.setPublicationYear(year);
//        mockMvc.perform(post("/api/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(book)))
//                .andExpect(status().isBadRequest());
//    }
//}
