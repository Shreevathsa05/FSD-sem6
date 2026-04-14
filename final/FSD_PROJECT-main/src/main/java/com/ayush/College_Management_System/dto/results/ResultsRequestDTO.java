package com.ayush.College_Management_System.dto.results;

import com.ayush.College_Management_System.model.enums.ResultStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultsRequestDTO {

    @NotNull
    private Double marksObtained;

    private Double internalMarks;
    private Double externalMarks;
    private String gradePoint;

    @NotNull
    private ResultStatus status;

    private Integer attemptNumber;

    @NotNull
    private Long studentId;

    @NotNull
    private Long examId;
}
