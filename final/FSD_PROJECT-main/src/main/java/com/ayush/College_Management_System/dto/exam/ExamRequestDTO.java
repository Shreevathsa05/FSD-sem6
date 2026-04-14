package com.ayush.College_Management_System.dto.exam;

import com.ayush.College_Management_System.model.enums.ExamType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamRequestDTO {

    @NotNull
    private ExamType examType;

    private String session;

    @NotNull
    private LocalDate examDate;

    private String roomNumber;
    private Integer maxMarks;

    @NotNull
    private Long subjectId;
}
