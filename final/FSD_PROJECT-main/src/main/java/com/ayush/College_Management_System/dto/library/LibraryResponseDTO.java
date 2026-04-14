package com.ayush.College_Management_System.dto.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LibraryResponseDTO {
    private Long id;
    private String name;
    private String location;
    private Integer totalBooks;
    private Integer totalSeats;
    private String openingTime;
    private String closingTime;
    private String librarianName;
}
