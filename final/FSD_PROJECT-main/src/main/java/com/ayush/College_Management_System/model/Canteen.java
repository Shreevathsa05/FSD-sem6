package com.ayush.College_Management_System.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "canteens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Canteen extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String location;
    private String operatingHours;
    private Integer seatingCapacity;
    private String managerName;
    private String contactNumber;
    private Boolean isVegetarian;
    private Boolean isActive = true;
}
