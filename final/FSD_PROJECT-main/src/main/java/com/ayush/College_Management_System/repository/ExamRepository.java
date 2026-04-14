package com.ayush.College_Management_System.repository;
import com.ayush.College_Management_System.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    long countByExamDateAfter(LocalDate date);
}