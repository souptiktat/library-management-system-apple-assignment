//package com.apple.library.exception;
//
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotBlank;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = GlobalExceptionHandlerTest.TestController.class)
//@Import(GlobalExceptionHandler.class)
//class GlobalExceptionHandlerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    // ---------- 404 NOT FOUND ----------
//
//    @Test
//    @WithMockUser
//    @DisplayName("Should return 404 when ResourceNotFoundException is thrown")
//    void shouldHandleResourceNotFoundException() throws Exception {
//        mockMvc.perform(get("/test/not-found"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status").value(404))
//                .andExpect(jsonPath("$.message").value("Book not found"))
//                .andExpect(jsonPath("$.errors").doesNotExist());
//    }
//
//    // ---------- 400 VALIDATION ERROR ----------
//
//    @Test
//    @WithMockUser
//    @DisplayName("Should return validation error response")
//    void shouldHandleValidationException() throws Exception {
//        mockMvc.perform(post("/test/validation")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                                {
//                                  "name": ""
//                                }
//                                """))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.status").value(400))
//                .andExpect(jsonPath("$.message").value("Validation failed"))
//                .andExpect(jsonPath("$.errors", hasSize(1)))
//                .andExpect(jsonPath("$.errors[0]", containsString("name")));
//    }
//
//    // --------------------------------------------------
//    // Test Controller
//    // --------------------------------------------------
//
//    @RestController
//    static class TestController {
//
//        @GetMapping("/test/not-found")
//        void notFound() {
//            throw new ResourceNotFoundException("Book not found");
//        }
//
//        @PostMapping("/test/validation")
//        void validate(@Valid @RequestBody TestRequest request) {
//        }
//    }
//
//    static class TestRequest {
//        @NotBlank
//        public String name;
//    }
//}
