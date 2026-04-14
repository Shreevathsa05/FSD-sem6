package com.ayush.College_Management_System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"students", "courses", "facultyMembers", "ugPrograms"})
public class Department extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String code;
    private String hodName;
    private String email;
    private String extensionNo;
    private LocalDate foundationDate;
    private Integer totalLabs;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Student> students;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Course> courses;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Faculty> facultyMembers;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<UGProgram> ugPrograms;
}