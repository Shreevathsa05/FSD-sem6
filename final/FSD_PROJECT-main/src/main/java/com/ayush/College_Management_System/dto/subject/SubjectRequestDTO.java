package com.ayush.College_Management_System.dto.subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubjectRequestDTO {

    @NotBlank
    private String subName;

    @NotBlank
    private String subCode;

    private String subType;

    @NotNull
    private Integer credits;

    private Integer maxMarks;
    private Integer passingMarks;

    @NotNull
    private Integer semesterNumber;

    // 🔗 relations
    @NotNull
    private Long courseId;

    @NotNull
    private Long facultyId;
}