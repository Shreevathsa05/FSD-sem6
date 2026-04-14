package com.ayush.College_Management_System.dto.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventsResponseDTO {

    private Long id;
    private String eventTitle;
    private String organizer;
    private String venue;
    private LocalDateTime dateTime;
    private String guestName;
    private Double budgetAllocated;
    private Integer participantsCount;
    private String departmentName;
}
