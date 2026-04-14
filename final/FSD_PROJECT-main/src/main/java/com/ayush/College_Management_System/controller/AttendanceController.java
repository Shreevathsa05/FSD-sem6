package com.ayush.College_Management_System.controller;

import com.ayush.College_Management_System.dto.attendance.*;
import com.ayush.College_Management_System.security.SecurityUserAccessor;
import com.ayush.College_Management_System.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final SecurityUserAccessor securityUserAccessor;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<AttendanceResponseDTO> create(@Valid @RequestBody AttendanceRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.create(dto));
    }

    /**
     * Declared before {@code /{id}} so paths like {@code /student/...} are not captured as numeric ids.
     */
    @GetMapping("/student/{studentId}/subject/{subjectId}")
    public AttendancePercentageResponse getSubjectPercentage(
            @PathVariable Long studentId,
            @PathVariable Long subjectId
    ) {
        return attendanceService.getSubjectPercentage(studentId, subjectId);
    }

    @GetMapping("/defaulters")
    public ResponseEntity<List<DefaulterResponse>> getDefaulters(
            @RequestParam(defaultValue = "75") double threshold,
            @RequestParam(required = false) Long departmentId) {
        return ResponseEntity.ok(attendanceService.getDefaulters(threshold, departmentId));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<AttendancePercentageResponse>> getMyAttendancePercentages() {
        Long linkedId = securityUserAccessor.getCurrentUser().getLinkedId();
        if (linkedId == null) {
            throw new AccessDeniedException("Student account must have linkedId set to the student record id");
        }
        return ResponseEntity.ok(attendanceService.getMySubjectPercentages(linkedId));
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<List<AttendanceResponseDTO>> getMyAttendanceRecords() {
        Long linkedId = securityUserAccessor.getCurrentUser().getLinkedId();
        if (linkedId == null) {
            throw new AccessDeniedException("Faculty account must have linkedId set to the faculty record id");
        }
        return ResponseEntity.ok(attendanceService.getBySubjectFacultyId(linkedId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceResponseDTO>> getAll() {
        return ResponseEntity.ok(attendanceService.getAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<AttendanceResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AttendanceRequestDTO dto) {
        return ResponseEntity.ok(attendanceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        attendanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<List<AttendanceResponseDTO>> bulkMark(
            @Valid @RequestBody BulkAttendanceRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(attendanceService.bulkMark(dto));
    }
}
