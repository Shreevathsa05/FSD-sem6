package com.ayush.College_Management_System.repository;

import com.ayush.College_Management_System.model.UGProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UGProgramRepository extends JpaRepository<UGProgram, Long> {
    List<UGProgram> findByDepartmentId(Long departmentId);
    List<UGProgram> findByDegreeType(String degreeType);
}
