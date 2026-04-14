package com.ayush.College_Management_System.dto.library;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LibraryRequestDTO {
    @NotBlank private String name;
    private String location;
    private Integer totalBooks;
    private Integer totalSeats;
    private String openingTime;
    private String closingTime;
    private Long librarianId;
}
