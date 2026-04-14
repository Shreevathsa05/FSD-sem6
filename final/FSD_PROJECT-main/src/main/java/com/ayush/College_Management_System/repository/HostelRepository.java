package com.ayush.College_Management_System.repository;

import com.ayush.College_Management_System.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Long> {
    Optional<Hostel> findByStudentId(Long studentId);
    java.util.List<Hostel> findByHostelName(String hostelName);
}
