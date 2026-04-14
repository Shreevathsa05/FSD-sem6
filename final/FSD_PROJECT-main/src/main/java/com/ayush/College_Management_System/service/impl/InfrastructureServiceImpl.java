package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.infrastructure.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.service.InfrastructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfrastructureServiceImpl implements InfrastructureService {

    private final InfrastructureRepository repo;
    private final DepartmentRepository deptRepo;

    @Override
    @Transactional
    public InfrastructureResponseDTO create(InfrastructureRequestDTO dto) {
        Department dept = deptRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Infrastructure e = new Infrastructure();
        map(e, dto);
        e.setDepartment(dept);
        return toResponse(repo.save(e));
    }

    @Override
    @Transactional(readOnly = true)
    public InfrastructureResponseDTO getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Infrastructure not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InfrastructureResponseDTO> getAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InfrastructureResponseDTO update(Long id, InfrastructureRequestDTO dto) {
        Infrastructure e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Infrastructure not found"));
        Department dept = deptRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        map(e, dto);
        e.setDepartment(dept);
        return toResponse(repo.save(e));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Infrastructure not found");
        repo.deleteById(id);
    }

    private void map(Infrastructure e, InfrastructureRequestDTO d) {
        e.setRoomOrLabName(d.getRoomOrLabName());
        e.setFloor(d.getFloor());
        e.setBlock(d.getBlock());
        e.setCapacity(d.getCapacity());
        e.setHasProjector(d.getHasProjector());
        e.setNoOfComputers(d.getNoOfComputers());
        e.setStatus(d.getStatus());
    }

    private InfrastructureResponseDTO toResponse(Infrastructure e) {
        InfrastructureResponseDTO d = new InfrastructureResponseDTO();
        d.setId(e.getId());
        d.setRoomOrLabName(e.getRoomOrLabName());
        d.setFloor(e.getFloor());
        d.setBlock(e.getBlock());
        d.setCapacity(e.getCapacity());
        d.setHasProjector(e.getHasProjector());
        d.setNoOfComputers(e.getNoOfComputers());
        d.setStatus(e.getStatus());
        d.setDepartmentName(e.getDepartment() != null ? e.getDepartment().getName() : null);
        return d;
    }
}
