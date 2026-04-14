package com.ayush.College_Management_System.repository;
import com.ayush.College_Management_System.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Faculty findByEmployeeCode(String employeeCode);
}