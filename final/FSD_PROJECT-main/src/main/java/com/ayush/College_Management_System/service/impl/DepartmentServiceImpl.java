package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.department.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.Department;
import com.ayush.College_Management_System.repository.DepartmentRepository;
import com.ayush.College_Management_System.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepo;

    @Override
    @Transactional
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {
        log.info("Creating department: {}", dto.getName());
        Department dept = new Department();
        mapToEntity(dept, dto);
        return mapToResponse(departmentRepo.save(dept));
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponseDTO getDepartmentById(Long id) {
        log.info("Fetching department with id: {}", id);
        Department dept = departmentRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department not found with id: {}", id);
                    return new ResourceNotFoundException("Department not found");
                });
        return mapToResponse(dept);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponseDTO> getAllDepartments() {
        log.info("Fetching all departments");
        return departmentRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO dto) {
        log.info("Updating department with id: {}", id);
        Department dept = departmentRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department not found with id: {}", id);
                    return new ResourceNotFoundException("Department not found");
                });
        mapToEntity(dept, dto);
        return mapToResponse(departmentRepo.save(dept));
    }

    @Override
    @Transactional
    public DepartmentResponseDTO patchDepartment(Long id, DepartmentRequestDTO dto) {
        log.info("Patching department with id: {}", id);
        Department dept = departmentRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department not found with id: {}", id);
                    return new ResourceNotFoundException("Department not found");
                });
        if (dto.getName() != null) {
            dept.setName(dto.getName());
        }
        if (dto.getCode() != null) {
            dept.setCode(dto.getCode());
        }
        if (dto.getHodName() != null) {
            dept.setHodName(dto.getHodName());
        }
        if (dto.getEmail() != null) {
            dept.setEmail(dto.getEmail());
        }
        if (dto.getExtensionNo() != null) {
            dept.setExtensionNo(dto.getExtensionNo());
        }
        return mapToResponse(departmentRepo.save(dept));
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        log.info("Deleting department with id: {}", id);
        if (!departmentRepo.existsById(id)) {
            log.warn("Department not found with id: {}", id);
            throw new ResourceNotFoundException("Department not found");
        }
        departmentRepo.deleteById(id);
    }

    private void mapToEntity(Department dept, DepartmentRequestDTO dto) {
        dept.setName(dto.getName());
        dept.setCode(dto.getCode());
        dept.setHodName(dto.getHodName());
        dept.setEmail(dto.getEmail());
        dept.setExtensionNo(dto.getExtensionNo());
    }

    private DepartmentResponseDTO mapToResponse(Department dept) {
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setId(dept.getId());
        dto.setName(dept.getName());
        dto.setCode(dept.getCode());
        dto.setHodName(dept.getHodName());
        dto.setEmail(dept.getEmail());
        return dto;
    }
}