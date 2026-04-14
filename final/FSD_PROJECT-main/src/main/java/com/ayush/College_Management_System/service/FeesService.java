package com.ayush.College_Management_System.service;

import com.ayush.College_Management_System.dto.fees.*;
import java.util.List;

public interface FeesService {
    FeesResponseDTO create(FeesRequestDTO dto);
    FeesResponseDTO getById(Long id);
    List<FeesResponseDTO> getAll();
    FeesResponseDTO update(Long id, FeesRequestDTO dto);
    void delete(Long id);

    List<FeesResponseDTO> getByStudentId(Long studentId);

    List<FeesResponseDTO> getOverdue(Long departmentId);
}
