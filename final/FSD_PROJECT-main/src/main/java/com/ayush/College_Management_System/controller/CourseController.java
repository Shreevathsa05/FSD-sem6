package com.ayush.College_Management_System.controller;

import com.ayush.College_Management_System.dto.course.*;
import com.ayush.College_Management_System.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CourseResponseDTO create(@Valid @RequestBody CourseRequestDTO dto) {
        log.info("API: Create Course");
        return courseService.createCourse(dto);
    }

    @GetMapping("/{id}")
    public CourseResponseDTO get(@PathVariable Long id) {
        log.info("API: Get Course {}", id);
        return courseService.getCourseById(id);
    }

    @GetMapping
    public List<CourseResponseDTO> getAll() {
        log.info("API: Get all Courses");
        return courseService.getAllCourses();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CourseResponseDTO update(@PathVariable Long id,
                                    @Valid @RequestBody CourseRequestDTO dto) {
        log.info("API: Update Course {}", id);
        return courseService.updateCourse(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CourseResponseDTO patch(@PathVariable Long id,
                                   @RequestBody CourseRequestDTO dto) {
        log.info("API: Patch Course {}", id);
        return courseService.patchCourse(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        log.info("API: Delete Course {}", id);
        courseService.deleteCourse(id);
    }
}