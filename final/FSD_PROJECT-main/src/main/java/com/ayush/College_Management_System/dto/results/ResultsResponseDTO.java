package com.ayush.College_Management_System.dto.results;

import com.ayush.College_Management_System.model.enums.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultsResponseDTO {

    private Long id;
    private Double marksObtained;
    private Double internalMarks;
    private Double externalMarks;
    private String gradePoint;
    private ResultStatus status;
    private Integer attemptNumber;
    private String studentName;
    private String studentRollNo;
    private String examSession;
    private String subjectName;
}
