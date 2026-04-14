package com.ayush.College_Management_System.service;
import com.ayush.College_Management_System.dto.classroom.*;
import java.util.List;
public interface ClassroomService {
    ClassroomResponseDTO create(ClassroomRequestDTO dto);
    ClassroomResponseDTO getById(Long id);
    List<ClassroomResponseDTO> getAll();
    ClassroomResponseDTO update(Long id, ClassroomRequestDTO dto);
    void delete(Long id);
}
