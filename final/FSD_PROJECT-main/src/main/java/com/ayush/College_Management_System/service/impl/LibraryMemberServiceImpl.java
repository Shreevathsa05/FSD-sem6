package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.librarymember.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.service.LibraryMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class LibraryMemberServiceImpl implements LibraryMemberService {
    private final LibraryMemberRepository repo;
    private final StudentRepository studentRepo;
    private final FacultyRepository facultyRepo;
    private final LibraryRepository libraryRepo;

    @Override @Transactional
    public LibraryMemberResponseDTO create(LibraryMemberRequestDTO dto) {
        LibraryMember e = new LibraryMember();
        e.setMemberId(dto.getMemberId());
        e.setLibrary(libraryRepo.findById(dto.getLibraryId()).orElseThrow(() -> new ResourceNotFoundException("Library not found")));
        if (dto.getStudentId() != null) e.setStudent(studentRepo.findById(dto.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student not found")));
        if (dto.getFacultyId() != null) e.setFaculty(facultyRepo.findById(dto.getFacultyId()).orElseThrow(() -> new ResourceNotFoundException("Faculty not found")));
        e.setMembershipStartDate(dto.getMembershipStartDate()); e.setMembershipEndDate(dto.getMembershipEndDate());
        if (dto.getIsActive() != null) e.setIsActive(dto.getIsActive());
        return toResponse(repo.save(e));
    }

    @Override @Transactional(readOnly = true)
    public LibraryMemberResponseDTO getById(Long id) {
        return toResponse(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("LibraryMember not found")));
    }

    @Override @Transactional(readOnly = true)
    public List<LibraryMemberResponseDTO> getAll() { return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList()); }

    @Override @Transactional
    public LibraryMemberResponseDTO update(Long id, LibraryMemberRequestDTO dto) {
        LibraryMember e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("LibraryMember not found"));
        e.setMemberId(dto.getMemberId());
        e.setMembershipStartDate(dto.getMembershipStartDate()); e.setMembershipEndDate(dto.getMembershipEndDate());
        if (dto.getIsActive() != null) e.setIsActive(dto.getIsActive());
        return toResponse(repo.save(e));
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("LibraryMember not found");
        repo.deleteById(id);
    }

    private LibraryMemberResponseDTO toResponse(LibraryMember e) {
        LibraryMemberResponseDTO d = new LibraryMemberResponseDTO();
        d.setId(e.getId()); d.setMemberId(e.getMemberId());
        if (e.getStudent() != null) { d.setMemberName(e.getStudent().getFirstName()); d.setMemberType("STUDENT"); }
        else if (e.getFaculty() != null) { d.setMemberName(e.getFaculty().getFullName()); d.setMemberType("FACULTY"); }
        d.setLibraryName(e.getLibrary() != null ? e.getLibrary().getName() : null);
        d.setMembershipStartDate(e.getMembershipStartDate()); d.setMembershipEndDate(e.getMembershipEndDate());
        d.setIsActive(e.getIsActive());
        return d;
    }
}
