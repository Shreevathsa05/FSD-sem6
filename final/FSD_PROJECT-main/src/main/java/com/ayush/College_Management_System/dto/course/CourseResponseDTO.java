package com.ayush.College_Management_System.dto.course;

import lombok.Data;

@Data
public class CourseResponseDTO {

    private Long id;
    private String courseTitle;
    private String stream;
    private Integer totalSemesters;

    private String departmentName;
}