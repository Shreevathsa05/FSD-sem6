package com.ayush.College_Management_System.repository;

import com.ayush.College_Management_System.model.Attendance;
import com.ayush.College_Management_System.model.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Total lectures for student + subject
    long countByStudentIdAndSubjectId(Long studentId, Long subjectId);

    // Present count
    long countByStudentIdAndSubjectIdAndStatus(
            Long studentId,
            Long subjectId,
            AttendanceStatus status
    );

    // Date range
    List<Attendance> findByStudentIdAndLectureDateBetween(
            Long studentId,
            LocalDate start,
            LocalDate end
    );

    // Defaulters (< threshold)
    @Query("""
        SELECT a.student.id
        FROM Attendance a
        GROUP BY a.student.id
        HAVING 
        (SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) * 1.0 /
         COUNT(a)) < :threshold
    """)
    List<Long> findDefaulters(double threshold);

    /**
     * Per student–subject attendance rollups. Student has firstName/lastName; subject field is subName (not subjectName).
     * Optional department filter without changing grouping semantics.
     */
    @Query("SELECT s.id, sub.id, s.firstName, s.lastName, sub.subName, " +
           "SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END), COUNT(a) " +
           "FROM Attendance a JOIN a.student s JOIN a.subject sub " +
           "WHERE (:departmentId IS NULL OR s.department.id = :departmentId) " +
           "GROUP BY s.id, sub.id, s.firstName, s.lastName, sub.subName")
    List<Object[]> findAttendanceSummaryGrouped(@Param("departmentId") Long departmentId);

    @Query("SELECT DISTINCT a.subject.id FROM Attendance a WHERE a.student.id = :studentId")
    List<Long> findDistinctSubjectIdsByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT a FROM Attendance a WHERE a.subject.faculty.id = :facultyId")
    List<Attendance> findBySubjectFacultyId(@Param("facultyId") Long facultyId);

    boolean existsByStudentIdAndSubjectIdAndLectureDateAndLectureNumber(Long studentId, Long subjectId, LocalDate lectureDate, Integer lectureNumber);
}