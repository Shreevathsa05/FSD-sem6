package com.ayush.College_Management_System.service;
import com.ayush.College_Management_System.dto.pgstudent.*;
import java.util.List;
public interface PGStudentService {
    PGStudentResponseDTO create(PGStudentRequestDTO dto);
    PGStudentResponseDTO getById(Long id);
    List<PGStudentResponseDTO> getAll();
    PGStudentResponseDTO update(Long id, PGStudentRequestDTO dto);
    void delete(Long id);
}
