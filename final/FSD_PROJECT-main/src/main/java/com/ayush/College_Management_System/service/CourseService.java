package com.ayush.College_Management_System.service;

import com.ayush.College_Management_System.dto.course.*;
import java.util.List;

public interface CourseService {

    CourseResponseDTO createCourse(CourseRequestDTO dto);

    CourseResponseDTO getCourseById(Long id);

    List<CourseResponseDTO> getAllCourses();

    CourseResponseDTO updateCourse(Long id, CourseRequestDTO dto);

    CourseResponseDTO patchCourse(Long id, CourseRequestDTO dto);

    void deleteCourse(Long id);
}