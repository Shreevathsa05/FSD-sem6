package com.ayush.College_Management_System.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequestDTO {

    @NotBlank
    private String courseTitle;

    private String stream;

    @NotNull
    private Integer totalSemesters;

    private Integer durationYears;
    private Integer minCredits;
    private String level;

    // 🔗 relation
    @NotNull
    private Long departmentId;
}