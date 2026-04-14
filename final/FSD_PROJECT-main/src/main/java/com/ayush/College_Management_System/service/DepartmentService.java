package com.ayush.College_Management_System.service;

import com.ayush.College_Management_System.dto.department.*;
import java.util.List;

public interface DepartmentService {

    DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto);

    DepartmentResponseDTO getDepartmentById(Long id);

    List<DepartmentResponseDTO> getAllDepartments();

    DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO dto);

    DepartmentResponseDTO patchDepartment(Long id, DepartmentRequestDTO dto);

    void deleteDepartment(Long id);
}