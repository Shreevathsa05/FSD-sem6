package com.ayush.College_Management_System.model;

import com.ayush.College_Management_System.model.enums.ResultStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "results",
        uniqueConstraints = @UniqueConstraint(columnNames = {"stud_id", "exam_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"student", "exam"})
public class Results extends BaseEntity {

    private Double marksObtained;
    private Double internalMarks;
    private Double externalMarks;
    private String gradePoint;

    @Enumerated(EnumType.STRING)
    private ResultStatus status;

    private Integer attemptNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stud_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;
}