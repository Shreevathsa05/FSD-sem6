package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.book.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repo;
    private final LibraryRepository libRepo;

    @Override @Transactional
    public BookResponseDTO create(BookRequestDTO dto) {
        Library lib = libRepo.findById(dto.getLibraryId()).orElseThrow(() -> new ResourceNotFoundException("Library not found"));
        Book e = new Book(); map(e, dto); e.setLibrary(lib);
        return toResponse(repo.save(e));
    }

    @Override @Transactional(readOnly = true)
    public BookResponseDTO getById(Long id) {
        return toResponse(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found")));
    }

    @Override @Transactional(readOnly = true)
    public List<BookResponseDTO> getAll() { return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList()); }

    @Override @Transactional
    public BookResponseDTO update(Long id, BookRequestDTO dto) {
        Book e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        Library lib = libRepo.findById(dto.getLibraryId()).orElseThrow(() -> new ResourceNotFoundException("Library not found"));
        map(e, dto); e.setLibrary(lib);
        return toResponse(repo.save(e));
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Book not found");
        repo.deleteById(id);
    }

    private void map(Book e, BookRequestDTO d) {
        e.setTitle(d.getTitle()); e.setAuthor(d.getAuthor()); e.setIsbn(d.getIsbn());
        e.setPublisher(d.getPublisher()); e.setEdition(d.getEdition());
        e.setTotalCopies(d.getTotalCopies()); e.setAvailableCopies(d.getAvailableCopies());
        e.setCategory(d.getCategory()); e.setShelfLocation(d.getShelfLocation());
    }

    private BookResponseDTO toResponse(Book e) {
        BookResponseDTO d = new BookResponseDTO();
        d.setId(e.getId()); d.setTitle(e.getTitle()); d.setAuthor(e.getAuthor()); d.setIsbn(e.getIsbn());
        d.setPublisher(e.getPublisher()); d.setEdition(e.getEdition());
        d.setTotalCopies(e.getTotalCopies()); d.setAvailableCopies(e.getAvailableCopies());
        d.setCategory(e.getCategory()); d.setShelfLocation(e.getShelfLocation());
        d.setLibraryName(e.getLibrary() != null ? e.getLibrary().getName() : null);
        return d;
    }
}
