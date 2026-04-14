package com.ayush.College_Management_System.repository;

import com.ayush.College_Management_System.model.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {
    Optional<LibraryMember> findByMemberId(String memberId);
    List<LibraryMember> findByStudentId(Long studentId);
    List<LibraryMember> findByFacultyId(Long facultyId);
    List<LibraryMember> findByLibraryId(Long libraryId);
}
