package com.ayush.College_Management_System.model;

import com.ayush.College_Management_System.model.enums.ClassroomType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"department"})
public class Classroom extends BaseEntity {

    @Column(nullable = false)
    private String roomNumber;

    private String building;
    private Integer floor;
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private ClassroomType classroomType;

    private Boolean hasProjector;
    private Boolean hasAC;
    private Boolean isAvailable = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department department;
}
