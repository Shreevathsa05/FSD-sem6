package com.ayush.College_Management_System.dto.attendance;

import com.ayush.College_Management_System.model.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequestDTO {

    @NotNull
    private LocalDate lectureDate;

    private LocalTime startTime;
    private LocalTime endTime;

    @NotNull
    private AttendanceStatus status;

    private String remarks;

    @NotNull
    private Integer lectureNumber;

    @NotNull
    private Integer semester;

    @NotNull
    private Long studentId;

    @NotNull
    private Long subjectId;
}
