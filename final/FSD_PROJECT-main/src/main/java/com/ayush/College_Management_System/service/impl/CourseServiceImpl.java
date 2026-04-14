package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.course.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.Course;
import com.ayush.College_Management_System.model.Department;
import com.ayush.College_Management_System.repository.CourseRepository;
import com.ayush.College_Management_System.repository.DepartmentRepository;
import com.ayush.College_Management_System.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;
    private final DepartmentRepository departmentRepo;

    @Override
    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO dto) {
        log.info("Creating course: {}", dto.getCourseTitle());

        Department dept = departmentRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> {
                    log.error("Department not found with id: {}", dto.getDepartmentId());
                    return new ResourceNotFoundException("Department not found");
                });

        Course course = new Course();
        mapToEntity(course, dto);
        course.setDepartment(dept);
        return mapToResponse(courseRepo.save(course));
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponseDTO getCourseById(Long id) {
        log.info("Fetching course with id: {}", id);

        Course course = courseRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Course not found with id: {}", id);
                    return new ResourceNotFoundException("Course not found");
                });
        return mapToResponse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getAllCourses() {
        log.info("Fetching all courses");
        return courseRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO dto) {
        log.info("Updating course with id: {}", id);

        Course course = courseRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Course not found with id: {}", id);
                    return new ResourceNotFoundException("Course not found");
                });

        Department dept = departmentRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        mapToEntity(course, dto);
        course.setDepartment(dept);
        return mapToResponse(courseRepo.save(course));
    }

    @Override
    @Transactional
    public CourseResponseDTO patchCourse(Long id, CourseRequestDTO dto) {
        log.info("Patching course with id: {}", id);

        Course course = courseRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Course not found with id: {}", id);
                    return new ResourceNotFoundException("Course not found");
                });

        if (dto.getCourseTitle() != null) {
            course.setCourseTitle(dto.getCourseTitle());
        }
        if (dto.getStream() != null) {
            course.setStream(dto.getStream());
        }
        if (dto.getTotalSemesters() != null) {
            course.setTotalSemesters(dto.getTotalSemesters());
        }
        if (dto.getDurationYears() != null) {
            course.setDurationYears(dto.getDurationYears());
        }
        if (dto.getMinCredits() != null) {
            course.setMinCredits(dto.getMinCredits());
        }
        if (dto.getLevel() != null) {
            course.setLevel(dto.getLevel());
        }
        if (dto.getDepartmentId() != null) {
            Department dept = departmentRepo.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            course.setDepartment(dept);
        }

        return mapToResponse(courseRepo.save(course));
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        log.info("Deleting course with id: {}", id);
        if (!courseRepo.existsById(id)) {
            log.warn("Course not found with id: {}", id);
            throw new ResourceNotFoundException("Course not found");
        }
        courseRepo.deleteById(id);
    }

    private void mapToEntity(Course course, CourseRequestDTO dto) {
        course.setCourseTitle(dto.getCourseTitle());
        course.setStream(dto.getStream());
        course.setTotalSemesters(dto.getTotalSemesters());
        course.setDurationYears(dto.getDurationYears());
        course.setMinCredits(dto.getMinCredits());
        course.setLevel(dto.getLevel());
    }

    private CourseResponseDTO mapToResponse(Course course) {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setCourseTitle(course.getCourseTitle());
        dto.setStream(course.getStream());
        dto.setTotalSemesters(course.getTotalSemesters());
        // FIX: null-safe department mapping
        dto.setDepartmentName(
                course.getDepartment() != null
                        ? course.getDepartment().getName()
                        : null
        );
        return dto;
    }
}