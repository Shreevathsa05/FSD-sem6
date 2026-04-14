package com.ayush.College_Management_System.repository;
import com.ayush.College_Management_System.model.Results;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultsRepository extends JpaRepository<Results, Long> {

    List<Results> findByStudent_Id(Long studentId);

    default List<Results> findByStudentId(Long studentId) {
        return findByStudent_Id(studentId);
    }
}