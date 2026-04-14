package com.ayush.College_Management_System.dto.subject;

import lombok.Data;

@Data
public class SubjectResponseDTO {

    private Long id;
    private String subName;
    private String subCode;
    private Integer credits;

    private String courseName;
    private String facultyName;
}