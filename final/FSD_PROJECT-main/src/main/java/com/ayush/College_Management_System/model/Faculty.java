package com.ayush.College_Management_System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "faculty")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"subjectsTaught", "department"})
public class Faculty extends BaseEntity {

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String employeeCode;

    private String designation;
    private String qualification;
    private LocalDate dateOfJoining;
    private Double salary;
    private String specialization;

    @Column(unique = true)
    private String email;

    private String phone;
    private String cabinNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "faculty")
    @JsonIgnore
    private List<Subject> subjectsTaught;
}