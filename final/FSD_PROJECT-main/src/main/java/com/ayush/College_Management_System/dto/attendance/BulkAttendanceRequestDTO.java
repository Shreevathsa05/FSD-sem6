package com.ayush.College_Management_System.dto.attendance;

import com.ayush.College_Management_System.model.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkAttendanceRequestDTO {

    @NotNull
    private Long subjectId;
    @NotNull
    private LocalDate date;
    private Integer lectureNumber;
    private List<AttendanceRecord> records;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttendanceRecord {
        private Long studentId;
        private AttendanceStatus status;
    }
}
