package com.ayush.College_Management_System.service;

import com.ayush.College_Management_System.dto.faculty.*;
import java.util.List;

public interface FacultyService {
    FacultyResponseDTO create(FacultyRequestDTO dto);
    FacultyResponseDTO getById(Long id);
    List<FacultyResponseDTO> getAll();
    FacultyResponseDTO update(Long id, FacultyRequestDTO dto);
    void delete(Long id);
}
