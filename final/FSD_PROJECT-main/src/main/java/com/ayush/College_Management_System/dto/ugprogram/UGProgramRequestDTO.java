package com.ayush.College_Management_System.dto.ugprogram;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UGProgramRequestDTO {
    @NotBlank private String programName;
    private String degreeType;
    private Integer durationYears;
    private Integer totalSemesters;
    private Integer totalCredits;
    private String eligibilityCriteria;
    private Double annualFee;
    private Boolean isActive;
    @NotNull private Long departmentId;
}
