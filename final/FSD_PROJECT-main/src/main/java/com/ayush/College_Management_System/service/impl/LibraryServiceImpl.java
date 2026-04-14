package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.library.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository repo;
    private final FacultyRepository facultyRepo;

    @Override @Transactional
    public LibraryResponseDTO create(LibraryRequestDTO dto) {
        Library e = new Library();
        map(e, dto);
        if (dto.getLibrarianId() != null) {
            e.setLibrarian(facultyRepo.findById(dto.getLibrarianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty not found")));
        }
        return toResponse(repo.save(e));
    }

    @Override @Transactional(readOnly = true)
    public LibraryResponseDTO getById(Long id) {
        return toResponse(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Library not found")));
    }

    @Override @Transactional(readOnly = true)
    public List<LibraryResponseDTO> getAll() { return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList()); }

    @Override @Transactional
    public LibraryResponseDTO update(Long id, LibraryRequestDTO dto) {
        Library e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Library not found"));
        map(e, dto);
        if (dto.getLibrarianId() != null) {
            e.setLibrarian(facultyRepo.findById(dto.getLibrarianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty not found")));
        }
        return toResponse(repo.save(e));
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Library not found");
        repo.deleteById(id);
    }

    private void map(Library e, LibraryRequestDTO d) {
        e.setName(d.getName()); e.setLocation(d.getLocation());
        e.setTotalBooks(d.getTotalBooks()); e.setTotalSeats(d.getTotalSeats());
        e.setOpeningTime(d.getOpeningTime()); e.setClosingTime(d.getClosingTime());
    }

    private LibraryResponseDTO toResponse(Library e) {
        LibraryResponseDTO d = new LibraryResponseDTO();
        d.setId(e.getId()); d.setName(e.getName()); d.setLocation(e.getLocation());
        d.setTotalBooks(e.getTotalBooks()); d.setTotalSeats(e.getTotalSeats());
        d.setOpeningTime(e.getOpeningTime()); d.setClosingTime(e.getClosingTime());
        d.setLibrarianName(e.getLibrarian() != null ? e.getLibrarian().getFullName() : null);
        return d;
    }
}
