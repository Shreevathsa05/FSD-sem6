package com.ayush.College_Management_System.repository;

import com.ayush.College_Management_System.model.PGStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PGStudentRepository extends JpaRepository<PGStudent, Long> {
    List<PGStudent> findBySupervisorName(String supervisorName);
    List<PGStudent> findByIsThesisSubmitted(Boolean isThesisSubmitted);
}
