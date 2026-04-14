package com.ayush.College_Management_System.repository;

import com.ayush.College_Management_System.model.Classroom;
import com.ayush.College_Management_System.model.enums.ClassroomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findByDepartmentId(Long departmentId);
    List<Classroom> findByClassroomType(ClassroomType classroomType);
    List<Classroom> findByIsAvailable(Boolean isAvailable);
}
