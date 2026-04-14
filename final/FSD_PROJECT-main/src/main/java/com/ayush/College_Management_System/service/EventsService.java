package com.ayush.College_Management_System.service;

import com.ayush.College_Management_System.dto.events.*;
import java.util.List;

public interface EventsService {
    EventsResponseDTO create(EventsRequestDTO dto);
    EventsResponseDTO getById(Long id);
    List<EventsResponseDTO> getAll();
    EventsResponseDTO update(Long id, EventsRequestDTO dto);
    void delete(Long id);
}
