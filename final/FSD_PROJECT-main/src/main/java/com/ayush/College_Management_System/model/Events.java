package com.ayush.College_Management_System.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"department"})
public class Events extends BaseEntity {

    private String eventTitle;
    private String organizer;
    private String venue;
    private LocalDateTime dateTime;
    private String guestName;
    private Double budgetAllocated;
    private Integer participantsCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;
}