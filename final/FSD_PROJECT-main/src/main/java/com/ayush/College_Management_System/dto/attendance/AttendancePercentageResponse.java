package com.ayush.College_Management_System.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendancePercentageResponse {
    private Long studentId;
    private Long subjectId;
    private double percentage;
    private long totalClasses;
    private long attendedClasses;
}