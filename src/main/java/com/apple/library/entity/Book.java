package com.apple.library.entity;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Book",
        description = "Represents a book resource in the Library Management System"
)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Unique identifier of the book",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title length must be between 1 and 200 characters")
    @Schema(
            description = "Title of the book",
            example = "Effective Java",
            maxLength = 200,
            requiredMode = Schema.RequiredMode.REQUIRED,
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = "x-domain", value = "library")
                    })
            }
    )
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 100, message = "Author length must be between 2 and 100 characters")
    @Schema(
            description = "Author of the book",
            example = "Joshua Bloch",
            maxLength = 100,
            requiredMode = Schema.RequiredMode.REQUIRED,
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = "x-domain", value = "library")
                    })
            }
    )
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(
            regexp = "^(97(8|9))?\\d{9}(\\d|X)$",
            message = "Invalid ISBN format"
    )
    @Column(unique = true)
    @Schema(
            description = "ISBN-10 or ISBN-13 identifier of the book",
            example = "9780134685991",
            requiredMode = Schema.RequiredMode.REQUIRED,
            extensions = {
                    @Extension(properties = {
                            @ExtensionProperty(name = "x-identifier", value = "ISBN")
                    })
            }
    )
    private String isbn;

    @NotNull(message = "Publication year is required")
    @Min(value = 1450, message = "Publication year must be >= 1450")
    @Schema(
            description = "Year in which the book was published",
            example = "2018",
            minimum = "1450",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer publicationYear;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Genre is required")
    @Schema(
            description = "Genre classification of the book",
            example = "SCIENCE",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {
                    "FICTION",
                    "NON_FICTION",
                    "SCIENCE",
                    "HISTORY",
                    "BIOGRAPHY"
            }
    )
    private Genre genre;

    @Min(value = 0, message = "Available copies must be >= 0")
    @Schema(
            description = "Number of copies currently available for lending",
            example = "5",
            minimum = "0"
    )
    private int availableCopies;

    @Min(value = 1, message = "Total copies must be >= 1")
    @Schema(
            description = "Total number of copies owned by the library",
            example = "10",
            minimum = "1"
    )
    private int totalCopies;

    // publication year upper bound
    @AssertTrue(message = "Publication year must be between 1450 and next year")
    @Schema(hidden = true)
    private boolean isPublicationYearValid() {
        if (publicationYear == null) {
            return true;
        }
        return publicationYear <= Year.now().getValue() + 1;
    }

    // copies validation moved to Bean Validation
    @AssertTrue(message = "Available copies cannot exceed total copies")
    @Schema(hidden = true)
    private boolean isCopiesValid() {
        return availableCopies <= totalCopies;
    }
}
