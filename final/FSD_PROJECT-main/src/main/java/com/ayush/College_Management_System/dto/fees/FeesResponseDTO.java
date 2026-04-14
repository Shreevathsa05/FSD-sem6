package com.ayush.College_Management_System.dto.fees;

import com.ayush.College_Management_System.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeesResponseDTO {

    private Long id;
    private String transactionId;
    private String category;
    private Double totalAmount;
    private Double amountPaid;
    private LocalDate dueDate;
    private String paymentMode;
    private PaymentStatus paymentStatus;
    private String studentName;
    private String studentRollNo;
}
