package com.ayush.College_Management_System.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"subjects", "department"})
public class Course extends BaseEntity {

    private String courseTitle;
    private String stream;
    private Integer totalSemesters;
    private Integer durationYears;
    private Integer minCredits;
    private String level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects;
}