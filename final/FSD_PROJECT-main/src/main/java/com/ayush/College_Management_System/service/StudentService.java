package com.ayush.College_Management_System.service;

import com.ayush.College_Management_System.dto.student.StudentRequestDTO;
import com.ayush.College_Management_System.dto.student.StudentResponseDTO;

import java.util.List;

public interface StudentService {

    StudentResponseDTO createStudent(StudentRequestDTO dto);

    StudentResponseDTO getStudentById(Long id);

    List<StudentResponseDTO> getAllStudents();

    StudentResponseDTO updateStudent(Long id, StudentRequestDTO dto);

    StudentResponseDTO patchStudent(Long id, StudentRequestDTO dto);

    void deleteStudent(Long id);
}