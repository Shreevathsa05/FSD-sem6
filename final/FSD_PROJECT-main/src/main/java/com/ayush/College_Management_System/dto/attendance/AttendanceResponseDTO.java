package com.ayush.College_Management_System.dto.attendance;

import com.ayush.College_Management_System.model.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponseDTO {

    private Long id;
    private LocalDate lectureDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private AttendanceStatus status;
    private String remarks;
    private Integer lectureNumber;
    private Integer semester;
    private String studentName;
    private String studentRollNo;
    private String subjectName;
    private String subjectCode;
}
