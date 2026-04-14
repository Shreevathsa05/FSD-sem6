package com.ayush.College_Management_System.repository;

import com.ayush.College_Management_System.model.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {

    long countByReturnDateIsNull();

    List<BookIssue> findByMemberId(Long memberId);
    List<BookIssue> findByBookId(Long bookId);
    List<BookIssue> findByIsReturned(Boolean isReturned);
}
