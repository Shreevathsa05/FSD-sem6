package com.ayush.College_Management_System.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaulterResponse {
    private Long studentId;
    private String studentName;
    private Long subjectId;
    private String subjectName;
    private long presentCount;
    private long totalCount;
    private double percentage;
}
