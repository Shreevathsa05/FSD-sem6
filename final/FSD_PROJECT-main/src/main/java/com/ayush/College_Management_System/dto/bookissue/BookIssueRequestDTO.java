package com.ayush.College_Management_System.dto.bookissue;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
public class BookIssueRequestDTO {
    @NotNull private Long bookId;
    @NotNull private Long memberId;
    @NotNull private LocalDate issueDate;
    @NotNull private LocalDate dueDate;
    private LocalDate returnDate;
    private Double fineAmount;
    private Boolean isReturned;
}
