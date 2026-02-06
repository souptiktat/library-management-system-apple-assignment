package com.apple.library.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(
        name = "ApiError",
        description = "Standard error response returned when an API request fails"
)
public record ApiError(
        @Schema(
                description = "Timestamp when the error occurred",
                example = "2026-02-05T14:32:10.123"
        )
        LocalDateTime timestamp,

        @Schema(
                description = "HTTP status code",
                example = "400"
        )
        int status,

        @Schema(
                description = "High-level error message",
                example = "Validation failed"
        )
        String error,

        @Schema(
                description = "Detailed error messages (field-level or business errors)",
                example = "[\"isbn: must not be blank\", \"title: size must be between 3 and 100\"]"
        )
        List<String> details
) {
}
