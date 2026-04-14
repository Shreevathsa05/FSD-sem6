package com.ayush.College_Management_System.dto.exam;

import com.ayush.College_Management_System.model.enums.ExamType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResponseDTO {

    private Long id;
    private ExamType examType;
    private String session;
    private LocalDate examDate;
    private String roomNumber;
    private Integer maxMarks;
    private String subjectName;
    private String subjectCode;
}
