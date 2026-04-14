package com.ayush.College_Management_System.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BookResponseDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Integer edition;
    private Integer totalCopies;
    private Integer availableCopies;
    private String category;
    private String shelfLocation;
    private String libraryName;
}
