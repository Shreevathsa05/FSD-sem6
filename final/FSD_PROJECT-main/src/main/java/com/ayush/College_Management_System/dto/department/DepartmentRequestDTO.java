package com.ayush.College_Management_System.dto.department;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentRequestDTO {

    @NotBlank
    private String name;

    private String code;
    private String hodName;
    private String email;
    private String extensionNo;
}