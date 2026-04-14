package com.ayush.College_Management_System.controller;

import com.ayush.College_Management_System.dto.results.*;
import com.ayush.College_Management_System.security.SecurityUserAccessor;
import com.ayush.College_Management_System.service.ResultsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultsController {

    private final ResultsService service;
    private final SecurityUserAccessor securityUserAccessor;

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<ResultsResponseDTO>> getMyResults() {
        Long linkedId = securityUserAccessor.getCurrentUser().getLinkedId();
        if (linkedId == null) {
            throw new AccessDeniedException("Student account must have linkedId set to the student record id");
        }
        return ResponseEntity.ok(service.getByStudentId(linkedId));
    }

    @GetMapping("/student/{studentId}/summary")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ResultSummaryDTO> getSummary(@PathVariable Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        return ResponseEntity.ok(service.getSummaryByStudentId(studentId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ResultsResponseDTO> create(@Valid @RequestBody ResultsRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultsResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResultsResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ResultsResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ResultsRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
