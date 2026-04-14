package com.ayush.College_Management_System.service;
import com.ayush.College_Management_System.dto.canteen.*;
import java.util.List;
public interface CanteenService {
    CanteenResponseDTO create(CanteenRequestDTO dto);
    CanteenResponseDTO getById(Long id);
    List<CanteenResponseDTO> getAll();
    CanteenResponseDTO update(Long id, CanteenRequestDTO dto);
    void delete(Long id);
}
