package com.ayush.College_Management_System.dto.faculty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyRequestDTO {

    @NotBlank
    private String fullName;

    private String employeeCode;
    private String designation;
    private String qualification;
    private LocalDate dateOfJoining;
    private Double salary;
    private String specialization;

    @Email
    private String email;

    private String phone;
    private String cabinNo;

    @NotNull
    private Long departmentId;
}
