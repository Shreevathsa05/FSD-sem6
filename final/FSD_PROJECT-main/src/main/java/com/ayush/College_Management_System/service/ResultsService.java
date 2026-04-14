package com.ayush.College_Management_System.service;

import com.ayush.College_Management_System.dto.results.*;
import java.util.List;

public interface ResultsService {
    ResultsResponseDTO create(ResultsRequestDTO dto);
    ResultsResponseDTO getById(Long id);
    List<ResultsResponseDTO> getAll();
    ResultsResponseDTO update(Long id, ResultsRequestDTO dto);
    void delete(Long id);

    List<ResultsResponseDTO> getByStudentId(Long studentId);

    ResultSummaryDTO getSummaryByStudentId(Long studentId);
}
