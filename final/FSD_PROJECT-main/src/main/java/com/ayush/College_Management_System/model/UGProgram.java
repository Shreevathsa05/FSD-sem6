package com.ayush.College_Management_System.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ug_programs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"department"})
public class UGProgram extends BaseEntity {

    @Column(nullable = false)
    private String programName;

    private String degreeType; // B.Tech, B.Sc, BCA, etc.
    private Integer durationYears;
    private Integer totalSemesters;
    private Integer totalCredits;
    private String eligibilityCriteria;
    private Double annualFee;

    @Column(nullable = false)
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;
}
