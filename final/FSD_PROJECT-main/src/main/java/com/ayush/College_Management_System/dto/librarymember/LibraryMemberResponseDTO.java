package com.ayush.College_Management_System.dto.librarymember;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
public class LibraryMemberResponseDTO {
    private Long id;
    private String memberId;
    private String memberName;
    private String memberType; // STUDENT or FACULTY
    private String libraryName;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private Boolean isActive;
}
