package com.ayush.College_Management_System.repository;
import com.ayush.College_Management_System.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    java.util.List<Subject> findBySemesterNumber(Integer semester);

    /**
     * Subjects assigned to the faculty member ({@code Subject.faculty.id}).
     */
    java.util.List<Subject> findByFaculty_Id(Long facultyId);
}