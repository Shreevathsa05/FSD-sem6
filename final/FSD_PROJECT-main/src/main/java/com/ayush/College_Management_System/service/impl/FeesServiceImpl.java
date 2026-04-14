package com.ayush.College_Management_System.service.impl;

import com.ayush.College_Management_System.dto.fees.*;
import com.ayush.College_Management_System.exception.ResourceNotFoundException;
import com.ayush.College_Management_System.model.*;
import com.ayush.College_Management_System.model.enums.PaymentStatus;
import com.ayush.College_Management_System.repository.*;
import com.ayush.College_Management_System.service.FeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeesServiceImpl implements FeesService {

    private final FeesRepository repo;
    private final StudentRepository studentRepo;

    @Override
    @Transactional
    public FeesResponseDTO create(FeesRequestDTO dto) {
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Fees e = new Fees();
        map(e, dto);
        e.setStudent(student);
        return toResponse(repo.save(e));
    }

    @Override
    @Transactional(readOnly = true)
    public FeesResponseDTO getById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fee record not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeesResponseDTO> getAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FeesResponseDTO update(Long id, FeesRequestDTO dto) {
        Fees e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fee record not found"));
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        map(e, dto);
        e.setStudent(student);
        return toResponse(repo.save(e));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Fee record not found");
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeesResponseDTO> getByStudentId(Long studentId) {
        if (!studentRepo.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found");
        }
        return repo.findByStudent_Id(studentId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeesResponseDTO> getOverdue(Long departmentId) {
        List<Fees> list = departmentId == null
                ? repo.findByPaymentStatusNot(PaymentStatus.PAID)
                : repo.findByPaymentStatusNotAndStudent_Department_Id(PaymentStatus.PAID, departmentId);
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private void map(Fees e, FeesRequestDTO d) {
        e.setTransactionId(d.getTransactionId());
        e.setCategory(d.getCategory());
        e.setTotalAmount(d.getTotalAmount());
        e.setAmountPaid(d.getAmountPaid());
        e.setDueDate(d.getDueDate());
        e.setPaymentMode(d.getPaymentMode());
        e.setPaymentStatus(d.getPaymentStatus());
    }

    private FeesResponseDTO toResponse(Fees e) {
        FeesResponseDTO d = new FeesResponseDTO();
        d.setId(e.getId());
        d.setTransactionId(e.getTransactionId());
        d.setCategory(e.getCategory());
        d.setTotalAmount(e.getTotalAmount());
        d.setAmountPaid(e.getAmountPaid());
        d.setDueDate(e.getDueDate());
        d.setPaymentMode(e.getPaymentMode());
        d.setPaymentStatus(e.getPaymentStatus());
        if (e.getStudent() != null) {
            d.setStudentName(e.getStudent().getFirstName() + " " + (e.getStudent().getLastName() != null ? e.getStudent().getLastName() : ""));
            d.setStudentRollNo(e.getStudent().getRollNo());
        }
        return d;
    }
}
