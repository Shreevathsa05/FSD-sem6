package com.ayush.College_Management_System.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"course", "faculty"})
public class Subject extends BaseEntity {

    @Column(nullable = false)
    private String subName;

    @Column(unique = true, nullable = false)
    private String subCode;

    private String subType;
    private Integer credits;
    private Integer maxMarks;
    private Integer passingMarks;
    private Integer semesterNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
}