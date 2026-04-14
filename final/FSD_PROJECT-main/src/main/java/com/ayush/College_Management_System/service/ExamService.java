package com.ayush.College_Management_System.service;

import com.ayush.College_Management_System.dto.exam.*;
import java.util.List;

public interface ExamService {
    ExamResponseDTO create(ExamRequestDTO dto);
    ExamResponseDTO getById(Long id);
    List<ExamResponseDTO> getAll();
    ExamResponseDTO update(Long id, ExamRequestDTO dto);
    void delete(Long id);
}
