package com.ayush.College_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {

    private long totalStudents;
    private long totalFaculty;
    private long activeBooksIssued;
    private long unpaidFees;
    private long upcomingExams;
    private long upcomingEvents;
}
