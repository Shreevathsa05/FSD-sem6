package com.ayush.College_Management_System.dto.librarymember;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
public class LibraryMemberRequestDTO {
    @NotBlank private String memberId;
    private Long studentId;
    private Long facultyId;
    @NotNull private Long libraryId;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private Boolean isActive;
}
