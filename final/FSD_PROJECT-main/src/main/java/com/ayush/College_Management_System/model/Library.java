package com.ayush.College_Management_System.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "libraries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"librarian"})
public class Library extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String location;
    private Integer totalBooks;
    private Integer totalSeats;
    private String openingTime;
    private String closingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "librarian_id")
    private Faculty librarian;
}
