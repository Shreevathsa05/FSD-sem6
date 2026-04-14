package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.faculty.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository repo;
    private final DepartmentRepository deptRepo;

    @Override
    @Transactional
    public FacultyResponseDTO create(FacultyRequestDTO dto) {
        Department dept = deptRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Faculty e = new Faculty();
        map(e, dto);
        e.setDepartment(dept);
        return toResponse(repo.save(e));
    }

    @Override
    @Transactional(readOnly = true)
    public FacultyResponseDTO getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacultyResponseDTO> getAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FacultyResponseDTO update(Long id, FacultyRequestDTO dto) {
        Faculty e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
        Department dept = deptRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        map(e, dto);
        e.setDepartment(dept);
        return toResponse(repo.save(e));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Faculty not found");
        repo.deleteById(id);
    }

    private void map(Faculty e, FacultyRequestDTO d) {
        e.setFullName(d.getFullName());
        e.setEmployeeCode(d.getEmployeeCode());
        e.setDesignation(d.getDesignation());
        e.setQualification(d.getQualification());
        e.setDateOfJoining(d.getDateOfJoining());
        e.setSalary(d.getSalary());
        e.setSpecialization(d.getSpecialization());
        e.setEmail(d.getEmail());
        e.setPhone(d.getPhone());
        e.setCabinNo(d.getCabinNo());
    }

    private FacultyResponseDTO toResponse(Faculty e) {
        FacultyResponseDTO d = new FacultyResponseDTO();
        d.setId(e.getId());
        d.setFullName(e.getFullName());
        d.setEmployeeCode(e.getEmployeeCode());
        d.setDesignation(e.getDesignation());
        d.setQualification(e.getQualification());
        d.setDateOfJoining(e.getDateOfJoining());
        d.setSalary(e.getSalary());
        d.setSpecialization(e.getSpecialization());
        d.setEmail(e.getEmail());
        d.setPhone(e.getPhone());
        d.setCabinNo(e.getCabinNo());
        d.setDepartmentName(e.getDepartment() != null ? e.getDepartment().getName() : null);
        return d;
    }
}
