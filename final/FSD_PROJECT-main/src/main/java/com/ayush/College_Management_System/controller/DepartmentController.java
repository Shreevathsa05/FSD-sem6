package com.ayush.College_Management_System.controller;

import com.ayush.College_Management_System.dto.department.*;
import com.ayush.College_Management_System.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public DepartmentResponseDTO create(@Valid @RequestBody DepartmentRequestDTO dto) {
        log.info("API: Create Department");
        return departmentService.createDepartment(dto);
    }

    @GetMapping("/{id}")
    public DepartmentResponseDTO get(@PathVariable Long id) {
        log.info("API: Get Department {}", id);
        return departmentService.getDepartmentById(id);
    }

    @GetMapping
    public List<DepartmentResponseDTO> getAll() {
        log.info("API: Get all Departments");
        return departmentService.getAllDepartments();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DepartmentResponseDTO update(@PathVariable Long id,
                                        @Valid @RequestBody DepartmentRequestDTO dto) {
        log.info("API: Update Department {}", id);
        return departmentService.updateDepartment(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DepartmentResponseDTO patch(@PathVariable Long id,
                                       @RequestBody DepartmentRequestDTO dto) {
        log.info("API: Patch Department {}", id);
        return departmentService.patchDepartment(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        log.info("API: Delete Department {}", id);
        departmentService.deleteDepartment(id);
    }
}