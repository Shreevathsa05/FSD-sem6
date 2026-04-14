package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.bookissue.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.service.BookIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class BookIssueServiceImpl implements BookIssueService {
    private final BookIssueRepository repo;
    private final BookRepository bookRepo;
    private final LibraryMemberRepository memberRepo;

    @Override @Transactional
    public BookIssueResponseDTO create(BookIssueRequestDTO dto) {
        Book book = bookRepo.findById(dto.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        LibraryMember member = memberRepo.findById(dto.getMemberId()).orElseThrow(() -> new ResourceNotFoundException("Library member not found"));
        BookIssue e = new BookIssue(); e.setBook(book); e.setMember(member);
        e.setIssueDate(dto.getIssueDate()); e.setDueDate(dto.getDueDate());
        e.setReturnDate(dto.getReturnDate()); e.setFineAmount(dto.getFineAmount());
        if (dto.getIsReturned() != null) e.setIsReturned(dto.getIsReturned());
        return toResponse(repo.save(e));
    }

    @Override @Transactional(readOnly = true)
    public BookIssueResponseDTO getById(Long id) {
        return toResponse(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BookIssue not found")));
    }

    @Override @Transactional(readOnly = true)
    public List<BookIssueResponseDTO> getAll() { return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList()); }

    @Override @Transactional
    public BookIssueResponseDTO update(Long id, BookIssueRequestDTO dto) {
        BookIssue e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BookIssue not found"));
        e.setIssueDate(dto.getIssueDate()); e.setDueDate(dto.getDueDate());
        e.setReturnDate(dto.getReturnDate()); e.setFineAmount(dto.getFineAmount());
        if (dto.getIsReturned() != null) e.setIsReturned(dto.getIsReturned());
        return toResponse(repo.save(e));
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("BookIssue not found");
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public BookIssueResponseDTO returnBook(Long issueId) {
        BookIssue issue = repo.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("BookIssue not found"));
        if (issue.getReturnDate() != null) {
            throw new IllegalStateException("Book already returned");
        }
        LocalDate returnDate = LocalDate.now();
        LocalDate expectedReturnDate = issue.getIssueDate().plusDays(14);
        long daysOverdue = ChronoUnit.DAYS.between(expectedReturnDate, returnDate);
        if (daysOverdue > 0) {
            issue.setFineAmount(daysOverdue * 5.0);
        } else {
            issue.setFineAmount(null);
        }
        issue.setReturnDate(returnDate);
        issue.setIsReturned(true);
        return toResponse(repo.save(issue));
    }

    private BookIssueResponseDTO toResponse(BookIssue e) {
        BookIssueResponseDTO d = new BookIssueResponseDTO();
        d.setId(e.getId()); d.setBookTitle(e.getBook() != null ? e.getBook().getTitle() : null);
        d.setMemberName(e.getMember() != null ? e.getMember().getMemberId() : null);
        d.setIssueDate(e.getIssueDate()); d.setDueDate(e.getDueDate());
        d.setReturnDate(e.getReturnDate()); d.setFineAmount(e.getFineAmount());
        d.setIsReturned(e.getIsReturned());
        return d;
    }
}
