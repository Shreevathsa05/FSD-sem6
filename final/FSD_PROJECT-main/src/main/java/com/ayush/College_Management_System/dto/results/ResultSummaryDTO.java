package com.ayush.College_Management_System.dto.results;

import com.ayush.College_Management_System.model.enums.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultSummaryDTO {

    private Long studentId;
    private String studentName;
    private double overallPercentage;
    private String overallGrade;
    private List<SubjectResult> subjectResults = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubjectResult {
        private String subjectName;
        private Double marksObtained;
        private Integer totalMarks;
        private String grade;
        private ResultStatus status;
    }
}
