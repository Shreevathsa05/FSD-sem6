package com.ayush.College_Management_System.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "infrastructure")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"department"})
public class Infrastructure extends BaseEntity {

    private String roomOrLabName;
    private Integer floor;
    private String block;
    private Integer capacity;
    private Boolean hasProjector;
    private Integer noOfComputers;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;
}