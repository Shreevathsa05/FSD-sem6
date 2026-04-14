package com.ayush.College_Management_System.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hostel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"student"})
public class Hostel extends BaseEntity {

    @Column(nullable = false)
    private String hostelName;

    private String roomNumber;
    private Integer floorNumber;
    private String blockName;
    private String roomType; // Single, Double, Triple
    private Double messFee;
    private Double hostelFee;

    @Column(nullable = false)
    private Boolean isOccupied = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", unique = true)
    private Student student;
}
