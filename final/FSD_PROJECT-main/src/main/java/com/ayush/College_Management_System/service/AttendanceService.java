package com.ayush.College_Management_System.service;

import com.ayush.College_Management_System.dto.attendance.*;

import java.util.List;

public interface AttendanceService {

    AttendanceResponseDTO create(AttendanceRequestDTO dto);

    AttendanceResponseDTO getById(Long id);

    List<AttendanceResponseDTO> getAll();

    AttendanceResponseDTO update(Long id, AttendanceRequestDTO dto);

    void delete(Long id);

    /**
     * Calculates the attendance percentage for a specific student in a specific subject.
     * @param studentId The ID of the student
     * @param subjectId The ID of the subject
     * @return AttendancePercentageResponse containing stats and percentage
     */
    AttendancePercentageResponse getSubjectPercentage(Long studentId, Long subjectId);

    List<DefaulterResponse> getDefaulters(double threshold, Long departmentId);

    List<AttendanceResponseDTO> bulkMark(BulkAttendanceRequestDTO dto);

    /**
     * Attendance percentage per subject for the given student (subjects that have at least one attendance row).
     */
    List<AttendancePercentageResponse> getMySubjectPercentages(Long studentId);

    List<AttendanceResponseDTO> getBySubjectFacultyId(Long facultyId);
}