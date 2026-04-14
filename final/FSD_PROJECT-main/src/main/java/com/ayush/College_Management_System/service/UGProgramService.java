package com.ayush.College_Management_System.service;
import com.ayush.College_Management_System.dto.ugprogram.*;
import java.util.List;
public interface UGProgramService {
    UGProgramResponseDTO create(UGProgramRequestDTO dto);
    UGProgramResponseDTO getById(Long id);
    List<UGProgramResponseDTO> getAll();
    UGProgramResponseDTO update(Long id, UGProgramRequestDTO dto);
    void delete(Long id);
}
