package com.ayush.College_Management_System.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pg_students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"student"})
public class PGStudent extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Student student;

    @Column(nullable = false)
    private String researchTopic;

    private String supervisorName;
    private String thesisTitle;
    private LocalDate pgStartDate;
    private LocalDate expectedCompletionDate;
    private String programType; // M.Tech, PhD, etc.
    private Boolean isThesisSubmitted = false;
}
