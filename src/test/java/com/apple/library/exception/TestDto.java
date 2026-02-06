package com.apple.library.exception;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TestDto {
    @NotBlank(message = "title is mandatory")
    private String title;
}
