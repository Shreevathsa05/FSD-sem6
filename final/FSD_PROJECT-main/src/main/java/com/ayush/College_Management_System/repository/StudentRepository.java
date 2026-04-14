package com.ayush.College_Management_System.repository;
import com.ayush.College_Management_System.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Custom query to find a student by roll number
    Student findByRollNo(String rollNo);
    boolean existsByRollNo(String rollNo);
    boolean existsByEnrollmentNumber(String enrollmentNumber);
}