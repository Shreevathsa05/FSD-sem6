package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.results.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.model.enums.Grade;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.security.SecurityUserAccessor;
import com.ayush.College_Management_System.security.user.AppUserDetails;
import com.ayush.College_Management_System.security.user.Role;
import com.ayush.College_Management_System.service.ResultsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultsServiceImpl implements ResultsService {

    private final ResultsRepository repo;
    private final StudentRepository studentRepo;
    private final ExamRepository examRepo;
    private final SecurityUserAccessor securityUserAccessor;

    @Override
    @Transactional
    public ResultsResponseDTO create(ResultsRequestDTO dto) {
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Exam exam = examRepo.findById(dto.getExamId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));
        assertFacultyOwnsExamSubject(exam);
        Results e = new Results();
        map(e, dto);
        e.setStudent(student);
        e.setExam(exam);
        return toResponse(repo.save(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ResultsResponseDTO getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultsResponseDTO> getAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResultsResponseDTO update(Long id, ResultsRequestDTO dto) {
        Results e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found"));
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Exam exam = examRepo.findById(dto.getExamId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));
        assertFacultyOwnsExamSubject(exam);
        map(e, dto);
        e.setStudent(student);
        e.setExam(exam);
        return toResponse(repo.save(e));
    }

    private void assertFacultyOwnsExamSubject(Exam exam) {
        AppUserDetails user = securityUserAccessor.getCurrentUser();
        if (user.getRole() == Role.ADMIN) {
            return;
        }
        if (user.getRole() == Role.FACULTY) {
            if (user.getLinkedId() == null) {
                throw new AccessDeniedException(
                        "FACULTY users must have linkedId set to their faculty record id to manage results");
            }
            Subject subject = exam.getSubject();
            if (subject == null || subject.getFaculty() == null
                    || !user.getLinkedId().equals(subject.getFaculty().getId())) {
                throw new AccessDeniedException("You can only manage results for your own subjects");
            }
            return;
        }
        throw new AccessDeniedException("You do not have permission to manage results");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Result not found");
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultsResponseDTO> getByStudentId(Long studentId) {
        if (!studentRepo.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found");
        }
        return repo.findByStudent_Id(studentId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResultSummaryDTO getSummaryByStudentId(Long studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        List<Results> rows = repo.findByStudentId(studentId);

        List<ResultSummaryDTO.SubjectResult> subjectResults = new ArrayList<>();
        double sumMarks = 0.0;
        double sumMax = 0.0;

        for (Results r : rows) {
            Exam exam = r.getExam();
            Subject subject = exam != null ? exam.getSubject() : null;
            String subjectName = subject != null ? subject.getSubName() : null;
            Integer totalMarks = exam != null ? exam.getMaxMarks() : null;

            double obtained = r.getMarksObtained() != null ? r.getMarksObtained() : 0.0;
            int max = totalMarks != null && totalMarks > 0 ? totalMarks : 0;
            if (max > 0) {
                sumMarks += obtained;
                sumMax += max;
            }

            double pctRow = max > 0 ? (obtained * 100.0) / max : 0.0;
            String rowGrade = gradeFromPercentage(pctRow).name();

            subjectResults.add(new ResultSummaryDTO.SubjectResult(
                    subjectName,
                    r.getMarksObtained(),
                    totalMarks,
                    rowGrade,
                    r.getStatus()
            ));
        }

        double overallPercentage = sumMax > 0 ? (sumMarks * 100.0) / sumMax : 0.0;
        String overallGrade = gradeFromPercentage(overallPercentage).name();

        String studentName = (student.getFirstName() != null ? student.getFirstName() : "")
                + (student.getLastName() != null && !student.getLastName().isBlank()
                ? " " + student.getLastName()
                : "");
        studentName = studentName.trim();
        if (studentName.isEmpty()) {
            studentName = "N/A";
        }

        ResultSummaryDTO dto = new ResultSummaryDTO();
        dto.setStudentId(studentId);
        dto.setStudentName(studentName);
        dto.setOverallPercentage(overallPercentage);
        dto.setOverallGrade(overallGrade);
        dto.setSubjectResults(subjectResults);
        return dto;
    }

    /**
     * Maps percentage to {@link Grade} (10-point style bands on 100-scale).
     */
    private static Grade gradeFromPercentage(double percentage) {
        if (Double.isNaN(percentage)) {
            return Grade.INCOMPLETE;
        }
        if (percentage >= 90) {
            return Grade.A_PLUS;
        }
        if (percentage >= 80) {
            return Grade.A;
        }
        if (percentage >= 75) {
            return Grade.B_PLUS;
        }
        if (percentage >= 70) {
            return Grade.B;
        }
        if (percentage >= 65) {
            return Grade.C_PLUS;
        }
        if (percentage >= 60) {
            return Grade.C;
        }
        if (percentage >= 50) {
            return Grade.D;
        }
        return Grade.F;
    }

    private void map(Results e, ResultsRequestDTO d) {
        e.setMarksObtained(d.getMarksObtained());
        e.setInternalMarks(d.getInternalMarks());
        e.setExternalMarks(d.getExternalMarks());
        e.setGradePoint(d.getGradePoint());
        e.setStatus(d.getStatus());
        e.setAttemptNumber(d.getAttemptNumber());
    }

    private ResultsResponseDTO toResponse(Results e) {
        ResultsResponseDTO d = new ResultsResponseDTO();
        d.setId(e.getId());
        d.setMarksObtained(e.getMarksObtained());
        d.setInternalMarks(e.getInternalMarks());
        d.setExternalMarks(e.getExternalMarks());
        d.setGradePoint(e.getGradePoint());
        d.setStatus(e.getStatus());
        d.setAttemptNumber(e.getAttemptNumber());
        if (e.getStudent() != null) {
            d.setStudentName(e.getStudent().getFirstName() + " " + (e.getStudent().getLastName() != null ? e.getStudent().getLastName() : ""));
            d.setStudentRollNo(e.getStudent().getRollNo());
        }
        if (e.getExam() != null) {
            d.setExamSession(e.getExam().getSession());
            d.setSubjectName(e.getExam().getSubject() != null ? e.getExam().getSubject().getSubName() : null);
        }
        return d;
    }
}
