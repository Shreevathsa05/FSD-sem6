package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.exam.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.security.SecurityUserAccessor;
import com.ayush.College_Management_System.security.user.AppUserDetails;
import com.ayush.College_Management_System.security.user.Role;
import com.ayush.College_Management_System.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository repo;
    private final SubjectRepository subjectRepo;
    private final SecurityUserAccessor securityUserAccessor;

    @Override
    @Transactional
    public ExamResponseDTO create(ExamRequestDTO dto) {
        Subject subject = subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        assertFacultyOwnsSubjectForExam(subject);
        Exam e = new Exam();
        map(e, dto);
        e.setSubject(subject);
        return toResponse(repo.save(e));
    }

    @Override
    @Transactional(readOnly = true)
    public ExamResponseDTO getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResponseDTO> getAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ExamResponseDTO update(Long id, ExamRequestDTO dto) {
        Exam e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));
        Subject subject = subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        assertFacultyOwnsSubjectForExam(subject);
        map(e, dto);
        e.setSubject(subject);
        return toResponse(repo.save(e));
    }

    private void assertFacultyOwnsSubjectForExam(Subject subject) {
        AppUserDetails user = securityUserAccessor.getCurrentUser();
        if (user.getRole() == Role.ADMIN) {
            return;
        }
        if (user.getRole() == Role.FACULTY) {
            if (user.getLinkedId() == null) {
                throw new AccessDeniedException(
                        "FACULTY users must have linkedId set to their faculty record id to manage exams");
            }
            Faculty assigned = subject.getFaculty();
            if (assigned == null || !user.getLinkedId().equals(assigned.getId())) {
                throw new AccessDeniedException("You can only manage exams for your own subjects");
            }
            return;
        }
        throw new AccessDeniedException("You do not have permission to manage exams");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Exam not found");
        repo.deleteById(id);
    }

    private void map(Exam e, ExamRequestDTO d) {
        e.setExamType(d.getExamType());
        e.setSession(d.getSession());
        e.setExamDate(d.getExamDate());
        e.setRoomNumber(d.getRoomNumber());
        e.setMaxMarks(d.getMaxMarks());
    }

    private ExamResponseDTO toResponse(Exam e) {
        ExamResponseDTO d = new ExamResponseDTO();
        d.setId(e.getId());
        d.setExamType(e.getExamType());
        d.setSession(e.getSession());
        d.setExamDate(e.getExamDate());
        d.setRoomNumber(e.getRoomNumber());
        d.setMaxMarks(e.getMaxMarks());
        d.setSubjectName(e.getSubject() != null ? e.getSubject().getSubName() : null);
        d.setSubjectCode(e.getSubject() != null ? e.getSubject().getSubCode() : null);
        return d;
    }
}
