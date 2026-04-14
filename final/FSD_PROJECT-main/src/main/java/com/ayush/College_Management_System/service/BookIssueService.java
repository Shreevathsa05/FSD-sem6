package com.ayush.College_Management_System.service;
import com.ayush.College_Management_System.dto.bookissue.*;
import java.util.List;
public interface BookIssueService {
    BookIssueResponseDTO create(BookIssueRequestDTO dto);
    BookIssueResponseDTO getById(Long id);
    List<BookIssueResponseDTO> getAll();
    BookIssueResponseDTO update(Long id, BookIssueRequestDTO dto);
    void delete(Long id);

    BookIssueResponseDTO returnBook(Long issueId);
}
