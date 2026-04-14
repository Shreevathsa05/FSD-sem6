package com.ayush.College_Management_System.service;
import com.ayush.College_Management_System.dto.librarymember.*;
import java.util.List;
public interface LibraryMemberService {
    LibraryMemberResponseDTO create(LibraryMemberRequestDTO dto);
    LibraryMemberResponseDTO getById(Long id);
    List<LibraryMemberResponseDTO> getAll();
    LibraryMemberResponseDTO update(Long id, LibraryMemberRequestDTO dto);
    void delete(Long id);
}
