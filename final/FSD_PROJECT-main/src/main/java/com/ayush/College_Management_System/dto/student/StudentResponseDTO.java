package com.ayush.College_Management_System.dto.student;

import com.ayush.College_Management_System.model.enums.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentResponseDTO {

    private Long id;

    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dob;
    private String rollNo;
    private String email;
    private String phone;

    private Integer currentSemester;
    private Integer admissionYear;
    private StudentStatus status;
    private String address;

    // ── New fields ──
    private String enrollmentNumber;
    private AdmissionType admissionType;
    private BloodGroup bloodGroup;
    private String profileImageUrl;
    private Boolean isActive;

    private String departmentName;
    private String courseName;
}