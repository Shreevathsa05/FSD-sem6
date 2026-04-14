package com.ayush.College_Management_System.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BookRequestDTO {
    @NotBlank private String title;
    private String author;
    @NotBlank private String isbn;
    private String publisher;
    private Integer edition;
    private Integer totalCopies;
    private Integer availableCopies;
    private String category;
    private String shelfLocation;
    @NotNull private Long libraryId;
}
