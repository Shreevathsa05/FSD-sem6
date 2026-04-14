package com.ayush.College_Management_System.service;
import com.ayush.College_Management_System.dto.book.*;
import java.util.List;
public interface BookService {
    BookResponseDTO create(BookRequestDTO dto);
    BookResponseDTO getById(Long id);
    List<BookResponseDTO> getAll();
    BookResponseDTO update(Long id, BookRequestDTO dto);
    void delete(Long id);
}
