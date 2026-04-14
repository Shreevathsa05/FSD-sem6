package com.ayush.College_Management_System.model;

import com.ayush.College_Management_System.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "fees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"student"})
public class Fees extends BaseEntity {

    private String transactionId;
    private String category;
    private Double totalAmount;
    private Double amountPaid;
    private LocalDate dueDate;
    private String paymentMode;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stud_id", nullable = false)
    private Student student;
}