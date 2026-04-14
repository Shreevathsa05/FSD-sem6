package com.ayush.College_Management_System.dto.ugprogram;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UGProgramResponseDTO {
    private Long id;
    private String programName;
    private String degreeType;
    private Integer durationYears;
    private Integer totalSemesters;
    private Integer totalCredits;
    private String eligibilityCriteria;
    private Double annualFee;
    private Boolean isActive;
    private String departmentName;
}
