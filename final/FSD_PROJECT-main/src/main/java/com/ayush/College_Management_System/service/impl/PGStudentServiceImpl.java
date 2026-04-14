package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.pgstudent.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.service.PGStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PGStudentServiceImpl implements PGStudentService {
    private final PGStudentRepository repo;
    private final StudentRepository studentRepo;

    @Override @Transactional
    public PGStudentResponseDTO create(PGStudentRequestDTO dto) {
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        if (repo.existsById(dto.getStudentId())) {
            throw new IllegalStateException("PG record already exists for this student");
        }
        PGStudent e = new PGStudent();
        e.setStudent(student);
        map(e, dto);
        return toResponse(repo.save(e));
    }

    @Override @Transactional(readOnly = true)
    public PGStudentResponseDTO getById(Long id) {
        return toResponse(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("PGStudent not found")));
    }

    @Override @Transactional(readOnly = true)
    public List<PGStudentResponseDTO> getAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override @Transactional
    public PGStudentResponseDTO update(Long id, PGStudentRequestDTO dto) {
        if (repo.existsById(dto.getStudentId())) {
            throw new IllegalStateException("PG record already exists for this student");
        }
        PGStudent e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("PGStudent not found"));
        map(e, dto);
        return toResponse(repo.save(e));
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("PGStudent not found");
        repo.deleteById(id);
    }

    private void map(PGStudent e, PGStudentRequestDTO d) {
        e.setResearchTopic(d.getResearchTopic()); e.setSupervisorName(d.getSupervisorName());
        e.setThesisTitle(d.getThesisTitle()); e.setPgStartDate(d.getPgStartDate());
        e.setExpectedCompletionDate(d.getExpectedCompletionDate()); e.setProgramType(d.getProgramType());
        if (d.getIsThesisSubmitted() != null) e.setIsThesisSubmitted(d.getIsThesisSubmitted());
    }

    private PGStudentResponseDTO toResponse(PGStudent e) {
        PGStudentResponseDTO d = new PGStudentResponseDTO();
        d.setId(e.getId());
        d.setStudentName(e.getStudent() != null ? e.getStudent().getFirstName() + " " + (e.getStudent().getLastName() != null ? e.getStudent().getLastName() : "") : null);
        d.setRollNo(e.getStudent() != null ? e.getStudent().getRollNo() : null);
        d.setResearchTopic(e.getResearchTopic()); d.setSupervisorName(e.getSupervisorName());
        d.setThesisTitle(e.getThesisTitle()); d.setPgStartDate(e.getPgStartDate());
        d.setExpectedCompletionDate(e.getExpectedCompletionDate()); d.setProgramType(e.getProgramType());
        d.setIsThesisSubmitted(e.getIsThesisSubmitted());
        return d;
    }
}
