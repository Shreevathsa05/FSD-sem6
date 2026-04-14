package com.ayush.College_Management_System.dto.hostel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class HostelResponseDTO {
    private Long id;
    private String hostelName;
    private String roomNumber;
    private Integer floorNumber;
    private String blockName;
    private String roomType;
    private Double messFee;
    private Double hostelFee;
    private Boolean isOccupied;
    private String studentName;
    private String studentRollNo;
}
