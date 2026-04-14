package com.ayush.College_Management_System.dto.events;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventsRequestDTO {

    @NotBlank
    private String eventTitle;

    private String organizer;
    private String venue;
    private LocalDateTime dateTime;
    private String guestName;
    private Double budgetAllocated;
    private Integer participantsCount;

    @NotNull
    private Long departmentId;
}
