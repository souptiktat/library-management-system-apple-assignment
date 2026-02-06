package com.apple.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 200)
    private String title;

    @NotBlank
    @Size(min = 2, max = 100)
    private String author;

    @NotBlank
    @Pattern(
            regexp = "^(97(8|9))?\\d{9}(\\d|X)$",
            message = "Invalid ISBN format"
    )
    @Column(unique = true)
    private String isbn;

    @NotNull(message = "Publication year is required")
    @Min(value = 1450, message = "Publication year must be >= 1450")
    private Integer publicationYear;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Genre genre;

    @Min(value = 0, message = "Available copies must be >= 0")
    private int availableCopies;

    @Min(value = 1, message = "Total copies must be >= 1")
    private int totalCopies;

    // publication year upper bound
    @AssertTrue(message = "Publication year must be between 1450 and next year")
    private boolean isPublicationYearValid() {
        if (publicationYear == null) {
            return true;
        }
        return publicationYear <= Year.now().getValue() + 1;
    }

    // copies validation moved to Bean Validation
    @AssertTrue(message = "Available copies cannot exceed total copies")
    private boolean isCopiesValid() {
        return availableCopies <= totalCopies;
    }
}
