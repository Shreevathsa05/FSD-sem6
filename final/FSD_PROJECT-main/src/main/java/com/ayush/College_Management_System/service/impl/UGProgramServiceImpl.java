package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.ugprogram.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.service.UGProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class UGProgramServiceImpl implements UGProgramService {
    private final UGProgramRepository repo;
    private final DepartmentRepository deptRepo;

    @Override @Transactional
    public UGProgramResponseDTO create(UGProgramRequestDTO dto) {
        Department dept = deptRepo.findById(dto.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        UGProgram e = new UGProgram(); map(e, dto); e.setDepartment(dept);
        return toResponse(repo.save(e));
    }

    @Override @Transactional(readOnly = true)
    public UGProgramResponseDTO getById(Long id) {
        return toResponse(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("UGProgram not found")));
    }

    @Override @Transactional(readOnly = true)
    public List<UGProgramResponseDTO> getAll() { return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList()); }

    @Override @Transactional
    public UGProgramResponseDTO update(Long id, UGProgramRequestDTO dto) {
        UGProgram e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("UGProgram not found"));
        Department dept = deptRepo.findById(dto.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        map(e, dto); e.setDepartment(dept);
        return toResponse(repo.save(e));
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("UGProgram not found");
        repo.deleteById(id);
    }

    private void map(UGProgram e, UGProgramRequestDTO d) {
        e.setProgramName(d.getProgramName()); e.setDegreeType(d.getDegreeType());
        e.setDurationYears(d.getDurationYears()); e.setTotalSemesters(d.getTotalSemesters());
        e.setTotalCredits(d.getTotalCredits()); e.setEligibilityCriteria(d.getEligibilityCriteria());
        e.setAnnualFee(d.getAnnualFee());
        if (d.getIsActive() != null) e.setIsActive(d.getIsActive());
    }

    private UGProgramResponseDTO toResponse(UGProgram e) {
        UGProgramResponseDTO d = new UGProgramResponseDTO();
        d.setId(e.getId()); d.setProgramName(e.getProgramName()); d.setDegreeType(e.getDegreeType());
        d.setDurationYears(e.getDurationYears()); d.setTotalSemesters(e.getTotalSemesters());
        d.setTotalCredits(e.getTotalCredits()); d.setEligibilityCriteria(e.getEligibilityCriteria());
        d.setAnnualFee(e.getAnnualFee()); d.setIsActive(e.getIsActive());
        d.setDepartmentName(e.getDepartment() != null ? e.getDepartment().getName() : null);
        return d;
    }
}
