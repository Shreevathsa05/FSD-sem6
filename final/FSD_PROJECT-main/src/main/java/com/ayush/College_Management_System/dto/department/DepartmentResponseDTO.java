package com.ayush.College_Management_System.dto.department;

import lombok.Data;

@Data
public class DepartmentResponseDTO {

    private Long id;
    private String name;
    private String code;
    private String hodName;
    private String email;
}