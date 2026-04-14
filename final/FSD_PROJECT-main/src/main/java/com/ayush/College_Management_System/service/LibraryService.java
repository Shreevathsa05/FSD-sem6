package com.ayush.College_Management_System.service;
import com.ayush.College_Management_System.dto.library.*;
import java.util.List;
public interface LibraryService {
    LibraryResponseDTO create(LibraryRequestDTO dto);
    LibraryResponseDTO getById(Long id);
    List<LibraryResponseDTO> getAll();
    LibraryResponseDTO update(Long id, LibraryRequestDTO dto);
    void delete(Long id);
}
