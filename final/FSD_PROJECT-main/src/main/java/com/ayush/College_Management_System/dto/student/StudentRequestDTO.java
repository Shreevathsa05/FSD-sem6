package com.ayush.College_Management_System.dto.student;

import com.ayush.College_Management_System.model.enums.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRequestDTO {

    @NotBlank
    private String firstName;

    private String lastName;

    @NotNull
    private Gender gender;

    private LocalDate dob;

    @NotBlank
    private String rollNo;

    @Email
    private String email;

    private String phone;

    @NotNull
    private Integer currentSemester;

    @NotNull
    private Integer admissionYear;

    @NotNull
    private StudentStatus status;

    private String address;

    // ── New fields ──
    private String enrollmentNumber;
    private AdmissionType admissionType;
    private BloodGroup bloodGroup;
    private String profileImageUrl;

    // 🔗 Only IDs (IMPORTANT)
    @NotNull
    private Long departmentId;

    @NotNull
    private Long courseId;
}