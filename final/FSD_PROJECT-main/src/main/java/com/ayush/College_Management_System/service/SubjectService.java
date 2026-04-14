package com.ayush.College_Management_System.service;

import com.ayush.College_Management_System.dto.subject.*;
import java.util.List;

public interface SubjectService {

    SubjectResponseDTO createSubject(SubjectRequestDTO dto);

    SubjectResponseDTO getSubjectById(Long id);

    List<SubjectResponseDTO> getAllSubjects();

    SubjectResponseDTO updateSubject(Long id, SubjectRequestDTO dto);

    SubjectResponseDTO patchSubject(Long id, SubjectRequestDTO dto);

    void deleteSubject(Long id);

    List<SubjectResponseDTO> getByFacultyId(Long facultyId);
}