package com.ayush.College_Management_System.service;

import com.ayush.College_Management_System.dto.infrastructure.*;
import java.util.List;

public interface InfrastructureService {
    InfrastructureResponseDTO create(InfrastructureRequestDTO dto);
    InfrastructureResponseDTO getById(Long id);
    List<InfrastructureResponseDTO> getAll();
    InfrastructureResponseDTO update(Long id, InfrastructureRequestDTO dto);
    void delete(Long id);
}
