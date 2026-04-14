package com.ayush.College_Management_System.controller;

import com.ayush.College_Management_System.dto.subject.*;
import com.ayush.College_Management_System.security.SecurityUserAccessor;
import com.ayush.College_Management_System.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@Slf4j
public class SubjectController {

    private final SubjectService subjectService;
    private final SecurityUserAccessor securityUserAccessor;

    @GetMapping("/mine")
    @PreAuthorize("hasRole('FACULTY')")
    public List<SubjectResponseDTO> getMySubjects() {
        Long linkedId = securityUserAccessor.getCurrentUser().getLinkedId();
        if (linkedId == null) {
            throw new AccessDeniedException("Faculty account must have linkedId set to the faculty record id");
        }
        return subjectService.getByFacultyId(linkedId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public SubjectResponseDTO create(@Valid @RequestBody SubjectRequestDTO dto) {
        log.info("API: Create Subject");
        return subjectService.createSubject(dto);
    }

    @GetMapping("/{id}")
    public SubjectResponseDTO get(@PathVariable Long id) {
        log.info("API: Get Subject {}", id);
        return subjectService.getSubjectById(id);
    }

    @GetMapping
    public List<SubjectResponseDTO> getAll() {
        log.info("API: Get all Subjects");
        return subjectService.getAllSubjects();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SubjectResponseDTO update(@PathVariable Long id,
                                     @Valid @RequestBody SubjectRequestDTO dto) {
        log.info("API: Update Subject {}", id);
        return subjectService.updateSubject(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SubjectResponseDTO patch(@PathVariable Long id,
                                    @RequestBody SubjectRequestDTO dto) {
        log.info("API: Patch Subject {}", id);
        return subjectService.patchSubject(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        log.info("API: Delete Subject {}", id);
        subjectService.deleteSubject(id);
    }
}