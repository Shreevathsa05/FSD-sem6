package com.ayush.College_Management_System.dto.pgstudent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
public class PGStudentRequestDTO {
    @NotNull private Long studentId;
    @NotBlank private String researchTopic;
    private String supervisorName;
    private String thesisTitle;
    private LocalDate pgStartDate;
    private LocalDate expectedCompletionDate;
    private String programType;
    private Boolean isThesisSubmitted;
}
