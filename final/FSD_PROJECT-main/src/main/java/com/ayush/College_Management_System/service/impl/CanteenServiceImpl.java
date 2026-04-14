package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.canteen.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.Canteen;
import com.ayush.College_Management_System.repository.CanteenRepository;
import com.ayush.College_Management_System.service.CanteenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CanteenServiceImpl implements CanteenService {
    private final CanteenRepository repo;

    @Override @Transactional
    public CanteenResponseDTO create(CanteenRequestDTO dto) {
        Canteen e = new Canteen(); map(e, dto); return toResponse(repo.save(e));
    }

    @Override @Transactional(readOnly = true)
    public CanteenResponseDTO getById(Long id) {
        return toResponse(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Canteen not found")));
    }

    @Override @Transactional(readOnly = true)
    public List<CanteenResponseDTO> getAll() { return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList()); }

    @Override @Transactional
    public CanteenResponseDTO update(Long id, CanteenRequestDTO dto) {
        Canteen e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Canteen not found"));
        map(e, dto); return toResponse(repo.save(e));
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Canteen not found");
        repo.deleteById(id);
    }

    private void map(Canteen e, CanteenRequestDTO d) {
        e.setName(d.getName()); e.setLocation(d.getLocation()); e.setOperatingHours(d.getOperatingHours());
        e.setSeatingCapacity(d.getSeatingCapacity()); e.setManagerName(d.getManagerName());
        e.setContactNumber(d.getContactNumber()); e.setIsVegetarian(d.getIsVegetarian());
        if (d.getIsActive() != null) e.setIsActive(d.getIsActive());
    }

    private CanteenResponseDTO toResponse(Canteen e) {
        CanteenResponseDTO d = new CanteenResponseDTO();
        d.setId(e.getId()); d.setName(e.getName()); d.setLocation(e.getLocation());
        d.setOperatingHours(e.getOperatingHours()); d.setSeatingCapacity(e.getSeatingCapacity());
        d.setManagerName(e.getManagerName()); d.setContactNumber(e.getContactNumber());
        d.setIsVegetarian(e.getIsVegetarian()); d.setIsActive(e.getIsActive());
        return d;
    }
}
