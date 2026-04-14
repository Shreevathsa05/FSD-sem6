package com.ayush.College_Management_System.dto.faculty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyResponseDTO {

    private Long id;
    private String fullName;
    private String employeeCode;
    private String designation;
    private String qualification;
    private LocalDate dateOfJoining;
    private Double salary;
    private String specialization;
    private String email;
    private String phone;
    private String cabinNo;
    private String departmentName;
}
