package com.ayush.College_Management_System.dto.pgstudent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
public class PGStudentResponseDTO {
    private Long id;
    private String studentName;
    private String rollNo;
    private String researchTopic;
    private String supervisorName;
    private String thesisTitle;
    private LocalDate pgStartDate;
    private LocalDate expectedCompletionDate;
    private String programType;
    private Boolean isThesisSubmitted;
}
