package com.ayush.College_Management_System.dto.fees;

import com.ayush.College_Management_System.model.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeesRequestDTO {

    private String transactionId;
    private String category;

    @NotNull
    private Double totalAmount;

    private Double amountPaid;
    private LocalDate dueDate;
    private String paymentMode;

    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    private Long studentId;
}
