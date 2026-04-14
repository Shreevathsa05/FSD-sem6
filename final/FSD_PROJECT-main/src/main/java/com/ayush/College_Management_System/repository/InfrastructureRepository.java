package com.ayush.College_Management_System.repository;
import com.ayush.College_Management_System.model.Infrastructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfrastructureRepository extends JpaRepository<Infrastructure, Long> {
}