package com.ayush.College_Management_System.repository;

import com.ayush.College_Management_System.model.Fees;
import com.ayush.College_Management_System.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeesRepository extends JpaRepository<Fees, Long> {

    List<Fees> findByStudent_Id(Long studentId);

    long countByPaymentStatusNot(PaymentStatus status);

    List<Fees> findByPaymentStatusNot(PaymentStatus status);

    /**
     * Fees not PAID for students in the given department ({@code student.department.id}).
     */
    List<Fees> findByPaymentStatusNotAndStudent_Department_Id(PaymentStatus status, Long departmentId);
}