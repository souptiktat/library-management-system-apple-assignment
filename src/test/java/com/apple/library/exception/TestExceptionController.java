package com.apple.library.exception;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestExceptionController {

    @GetMapping("/not-found")
    public void notFound() {
        throw new ResourceNotFoundException("Book not found");
    }

    @PostMapping("/validation")
    public void validation(@Valid @RequestBody TestDto dto) {
        // validation will fail before method body
    }

    @GetMapping("/duplicate")
    public void duplicate() {
        throw new DuplicateResourceException("Duplicate book");
    }
}
