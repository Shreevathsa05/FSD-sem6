package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.events.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.service.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private final EventsRepository repo;
    private final DepartmentRepository deptRepo;

    @Override
    @Transactional
    public EventsResponseDTO create(EventsRequestDTO dto) {
        Department dept = deptRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Events e = new Events();
        map(e, dto);
        e.setDepartment(dept);
        return toResponse(repo.save(e));
    }

    @Override
    @Transactional(readOnly = true)
    public EventsResponseDTO getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventsResponseDTO> getAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventsResponseDTO update(Long id, EventsRequestDTO dto) {
        Events e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        Department dept = deptRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        map(e, dto);
        e.setDepartment(dept);
        return toResponse(repo.save(e));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Event not found");
        repo.deleteById(id);
    }

    private void map(Events e, EventsRequestDTO d) {
        e.setEventTitle(d.getEventTitle());
        e.setOrganizer(d.getOrganizer());
        e.setVenue(d.getVenue());
        e.setDateTime(d.getDateTime());
        e.setGuestName(d.getGuestName());
        e.setBudgetAllocated(d.getBudgetAllocated());
        e.setParticipantsCount(d.getParticipantsCount());
    }

    private EventsResponseDTO toResponse(Events e) {
        EventsResponseDTO d = new EventsResponseDTO();
        d.setId(e.getId());
        d.setEventTitle(e.getEventTitle());
        d.setOrganizer(e.getOrganizer());
        d.setVenue(e.getVenue());
        d.setDateTime(e.getDateTime());
        d.setGuestName(e.getGuestName());
        d.setBudgetAllocated(e.getBudgetAllocated());
        d.setParticipantsCount(e.getParticipantsCount());
        d.setDepartmentName(e.getDepartment() != null ? e.getDepartment().getName() : null);
        return d;
    }
}
