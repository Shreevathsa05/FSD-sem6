package com.ayush.College_Management_System.controller;

import com.ayush.College_Management_System.dto.DashboardStatsDTO;
import com.ayush.College_Management_System.model.enums.PaymentStatus;
import com.ayush.College_Management_System.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final BookIssueRepository bookIssueRepository;
    private final FeesRepository feesRepository;
    private final ExamRepository examRepository;
    private final EventsRepository eventsRepository;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardStatsDTO> getStats() {
        DashboardStatsDTO dto = new DashboardStatsDTO();
        dto.setTotalStudents(studentRepository.count());
        dto.setTotalFaculty(facultyRepository.count());
        dto.setActiveBooksIssued(bookIssueRepository.countByReturnDateIsNull());
        dto.setUnpaidFees(feesRepository.countByPaymentStatusNot(PaymentStatus.PAID));
        dto.setUpcomingExams(examRepository.countByExamDateAfter(LocalDate.now()));
        dto.setUpcomingEvents(eventsRepository.countByDateTimeAfter(LocalDateTime.now()));
        return ResponseEntity.ok(dto);
    }
}
