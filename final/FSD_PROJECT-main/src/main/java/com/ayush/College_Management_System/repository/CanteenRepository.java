package com.ayush.College_Management_System.repository;

import com.ayush.College_Management_System.model.Canteen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CanteenRepository extends JpaRepository<Canteen, Long> {
    Optional<Canteen> findByName(String name);
}
