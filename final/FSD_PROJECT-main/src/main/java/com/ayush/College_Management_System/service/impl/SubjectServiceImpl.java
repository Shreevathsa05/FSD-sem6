package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.subject.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepo;
    private final CourseRepository courseRepo;
    private final FacultyRepository facultyRepo;

    @Override
    @Transactional
    public SubjectResponseDTO createSubject(SubjectRequestDTO dto) {
        log.info("Creating subject: {}", dto.getSubName());

        Course course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> {
                    log.error("Course not found with id: {}", dto.getCourseId());
                    return new ResourceNotFoundException("Course not found");
                });

        Faculty faculty = facultyRepo.findById(dto.getFacultyId())
                .orElseThrow(() -> {
                    log.error("Faculty not found with id: {}", dto.getFacultyId());
                    return new ResourceNotFoundException("Faculty not found");
                });

        Subject subject = new Subject();
        mapToEntity(subject, dto);
        subject.setCourse(course);
        subject.setFaculty(faculty);
        return mapToResponse(subjectRepo.save(subject));
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectResponseDTO getSubjectById(Long id) {
        log.info("Fetching subject with id: {}", id);

        Subject subject = subjectRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Subject not found with id: {}", id);
                    return new ResourceNotFoundException("Subject not found");
                });
        return mapToResponse(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponseDTO> getAllSubjects() {
        log.info("Fetching all subjects");
        return subjectRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubjectResponseDTO updateSubject(Long id, SubjectRequestDTO dto) {
        log.info("Updating subject with id: {}", id);

        Subject subject = subjectRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Subject not found with id: {}", id);
                    return new ResourceNotFoundException("Subject not found");
                });

        Course course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Faculty faculty = facultyRepo.findById(dto.getFacultyId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));

        mapToEntity(subject, dto);
        subject.setCourse(course);
        subject.setFaculty(faculty);
        return mapToResponse(subjectRepo.save(subject));
    }

    @Override
    @Transactional
    public SubjectResponseDTO patchSubject(Long id, SubjectRequestDTO dto) {
        log.info("Patching subject with id: {}", id);

        Subject subject = subjectRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Subject not found with id: {}", id);
                    return new ResourceNotFoundException("Subject not found");
                });

        if (dto.getSubName() != null) {
            subject.setSubName(dto.getSubName());
        }
        if (dto.getSubCode() != null) {
            subject.setSubCode(dto.getSubCode());
        }
        if (dto.getSubType() != null) {
            subject.setSubType(dto.getSubType());
        }
        if (dto.getCredits() != null) {
            subject.setCredits(dto.getCredits());
        }
        if (dto.getMaxMarks() != null) {
            subject.setMaxMarks(dto.getMaxMarks());
        }
        if (dto.getPassingMarks() != null) {
            subject.setPassingMarks(dto.getPassingMarks());
        }
        if (dto.getSemesterNumber() != null) {
            subject.setSemesterNumber(dto.getSemesterNumber());
        }
        if (dto.getCourseId() != null) {
            Course course = courseRepo.findById(dto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            subject.setCourse(course);
        }
        if (dto.getFacultyId() != null) {
            Faculty faculty = facultyRepo.findById(dto.getFacultyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
            subject.setFaculty(faculty);
        }

        return mapToResponse(subjectRepo.save(subject));
    }

    @Override
    @Transactional
    public void deleteSubject(Long id) {
        log.info("Deleting subject with id: {}", id);
        if (!subjectRepo.existsById(id)) {
            log.warn("Subject not found with id: {}", id);
            throw new ResourceNotFoundException("Subject not found");
        }
        subjectRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponseDTO> getByFacultyId(Long facultyId) {
        if (!facultyRepo.existsById(facultyId)) {
            throw new ResourceNotFoundException("Faculty not found");
        }
        return subjectRepo.findByFaculty_Id(facultyId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private void mapToEntity(Subject subject, SubjectRequestDTO dto) {
        subject.setSubName(dto.getSubName());
        subject.setSubCode(dto.getSubCode());
        subject.setSubType(dto.getSubType());
        subject.setCredits(dto.getCredits());
        subject.setMaxMarks(dto.getMaxMarks());
        subject.setPassingMarks(dto.getPassingMarks());
        subject.setSemesterNumber(dto.getSemesterNumber());
    }

    private SubjectResponseDTO mapToResponse(Subject subject) {
        SubjectResponseDTO dto = new SubjectResponseDTO();
        dto.setId(subject.getId());
        dto.setSubName(subject.getSubName());
        dto.setSubCode(subject.getSubCode());
        dto.setCredits(subject.getCredits());
        // FIX: null-safe mapping
        dto.setCourseName(
                subject.getCourse() != null
                        ? subject.getCourse().getCourseTitle()
                        : null
        );
        dto.setFacultyName(
                subject.getFaculty() != null
                        ? subject.getFaculty().getFullName()
                        : null
        );
        return dto;
    }
}