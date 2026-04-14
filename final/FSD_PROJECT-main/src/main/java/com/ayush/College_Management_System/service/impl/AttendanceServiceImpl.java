package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.attendance.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.model.enums.AttendanceStatus;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.security.SecurityUserAccessor;
import com.ayush.College_Management_System.security.user.AppUserDetails;
import com.ayush.College_Management_System.security.user.Role;
import com.ayush.College_Management_System.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;
    private final SecurityUserAccessor securityUserAccessor;

    @Override
    @Transactional
    public AttendanceResponseDTO create(AttendanceRequestDTO dto) {
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Subject subject = subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        assertCanManageAttendanceForSubject(subject);
        Attendance e = new Attendance();
        map(e, dto);
        e.setStudent(student);
        e.setSubject(subject);
        return toResponse(attendanceRepository.save(e));
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceResponseDTO getById(Long id) {
        return toResponse(attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getAll() {
        return attendanceRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AttendanceResponseDTO update(Long id, AttendanceRequestDTO dto) {
        Attendance e = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Subject subject = subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        assertCanManageAttendanceForSubject(subject);
        map(e, dto);
        e.setStudent(student);
        e.setSubject(subject);
        return toResponse(attendanceRepository.save(e));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Attendance e = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));
        assertCanManageAttendanceForSubject(e.getSubject());
        attendanceRepository.delete(e);
    }

    private void assertCanManageAttendanceForSubject(Subject subject) {
        AppUserDetails user = securityUserAccessor.requireCurrentUser();
        if (user.getRole() == Role.ADMIN) {
            return;
        }
        if (user.getRole() == Role.FACULTY) {
            if (user.getLinkedId() == null) {
                throw new AccessDeniedException(
                        "FACULTY users must have linkedId set to their faculty record id to manage attendance");
            }
            Faculty assigned = subject.getFaculty();
            if (assigned == null || !user.getLinkedId().equals(assigned.getId())) {
                throw new AccessDeniedException("You can only manage attendance for subjects you teach");
            }
            return;
        }
        throw new AccessDeniedException("You do not have permission to manage attendance");
    }

    @Override
    @Transactional
    public List<AttendanceResponseDTO> bulkMark(BulkAttendanceRequestDTO dto) {
        AppUserDetails currentUser = securityUserAccessor.getCurrentUser();
        Subject subject = subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        if (currentUser.getRole() == Role.FACULTY) {
            if (currentUser.getLinkedId() == null) {
                throw new AccessDeniedException(
                        "FACULTY users must have linkedId set to their faculty record id to manage attendance");
            }
            Faculty assigned = subject.getFaculty();
            if (assigned == null || !currentUser.getLinkedId().equals(assigned.getId())) {
                throw new AccessDeniedException("You can only manage attendance for subjects you teach");
            }
        } else if (currentUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("You do not have permission to mark attendance");
        }

        List<BulkAttendanceRequestDTO.AttendanceRecord> records = dto.getRecords();
        if (records == null || records.isEmpty()) {
            return List.of();
        }

        LocalDate lectureDate = dto.getDate();
        Integer lectureNumber = dto.getLectureNumber() != null ? dto.getLectureNumber() : 1;
        List<AttendanceResponseDTO> out = new ArrayList<>(records.size());

        for (BulkAttendanceRequestDTO.AttendanceRecord rec : records) {
            Student student = studentRepo.findById(rec.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + rec.getStudentId()));

            Integer semester = student.getCurrentSemester() != null
                    ? student.getCurrentSemester()
                    : subject.getSemesterNumber();
            if (semester == null) {
                semester = 1;
            }
            boolean exists = attendanceRepository
                    .existsByStudentIdAndSubjectIdAndLectureDateAndLectureNumber(
                            rec.getStudentId(),
                            dto.getSubjectId(),
                            lectureDate,
                            lectureNumber
                    );

            if (exists) {
                throw new IllegalStateException(
                        "Attendance already exists for studentId=" + rec.getStudentId()
                                + ", subjectId=" + dto.getSubjectId()
                                + ", date=" + lectureDate
                                + ", lectureNumber=" + lectureNumber
                );
            }

            Attendance e = new Attendance();
            e.setLectureDate(lectureDate);
            e.setStatus(rec.getStatus());
            e.setLectureNumber(lectureNumber);
            e.setSemester(semester);
            e.setStudent(student);
            e.setSubject(subject);

            out.add(toResponse(attendanceRepository.save(e)));
        }

        return out;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DefaulterResponse> getDefaulters(double threshold, Long departmentId) {
        List<Object[]> rows = attendanceRepository.findAttendanceSummaryGrouped(departmentId);
        return rows.stream()
                .map(this::mapSummaryRowToDefaulter)
                .filter(d -> d.getPercentage() < threshold)
                .collect(Collectors.toList());
    }

    private DefaulterResponse mapSummaryRowToDefaulter(Object[] row) {
        Long studentId = ((Number) row[0]).longValue();
        Long subjectId = ((Number) row[1]).longValue();
        String first = row[2] != null ? (String) row[2] : "";
        String last = row[3] != null ? (String) row[3] : "";
        String subName = row[4] != null ? (String) row[4] : "";
        long presentCount = ((Number) row[5]).longValue();
        long totalCount = ((Number) row[6]).longValue();
        double percentage = totalCount == 0 ? 0.0 : (presentCount * 100.0) / totalCount;
        String studentName = (first + " " + last).trim();
        if (studentName.isEmpty()) {
            studentName = "N/A";
        }
        return new DefaulterResponse(
                studentId,
                studentName,
                subjectId,
                subName,
                presentCount,
                totalCount,
                percentage
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendancePercentageResponse> getMySubjectPercentages(Long studentId) {
        if (!studentRepo.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found");
        }
        List<Long> subjectIds = attendanceRepository.findDistinctSubjectIdsByStudentId(studentId);
        return subjectIds.stream()
                .map(subjectId -> getSubjectPercentage(studentId, subjectId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getBySubjectFacultyId(Long facultyId) {
        return attendanceRepository.findBySubjectFacultyId(facultyId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AttendancePercentageResponse getSubjectPercentage(Long studentId, Long subjectId) {

        // 1. Fetch total classes conducted for this subject for this student
        long total = attendanceRepository.countByStudentIdAndSubjectId(studentId, subjectId);

        // 2. Fetch count of classes where the student was marked PRESENT
        long present = attendanceRepository.countByStudentIdAndSubjectIdAndStatus(
                studentId,
                subjectId,
                AttendanceStatus.PRESENT
        );

        // 3. Calculate percentage (Handle division by zero)
        double percentage = (total == 0) ? 0.0 : (present * 100.0) / total;

        // 4. Return the DTO
        return new AttendancePercentageResponse(
                studentId,
                subjectId,
                percentage,
                total,
                present
        );
    }

    private void map(Attendance e, AttendanceRequestDTO d) {
        e.setLectureDate(d.getLectureDate());
        e.setStartTime(d.getStartTime());
        e.setEndTime(d.getEndTime());
        e.setStatus(d.getStatus());
        e.setRemarks(d.getRemarks());
        e.setLectureNumber(d.getLectureNumber());
        e.setSemester(d.getSemester());
    }

    private AttendanceResponseDTO toResponse(Attendance e) {
        AttendanceResponseDTO d = new AttendanceResponseDTO();
        d.setId(e.getId());
        d.setLectureDate(e.getLectureDate());
        d.setStartTime(e.getStartTime());
        d.setEndTime(e.getEndTime());
        d.setStatus(e.getStatus());
        d.setRemarks(e.getRemarks());
        d.setLectureNumber(e.getLectureNumber());
        d.setSemester(e.getSemester());
        if (e.getStudent() != null) {
            d.setStudentName(e.getStudent().getFirstName() + " " + (e.getStudent().getLastName() != null ? e.getStudent().getLastName() : ""));
            d.setStudentRollNo(e.getStudent().getRollNo());
        }
        if (e.getSubject() != null) {
            d.setSubjectName(e.getSubject().getSubName());
            d.setSubjectCode(e.getSubject().getSubCode());
        }
        return d;
    }
}