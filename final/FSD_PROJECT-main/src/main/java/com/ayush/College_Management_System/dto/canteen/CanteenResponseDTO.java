package com.ayush.College_Management_System.dto.canteen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CanteenResponseDTO {
    private Long id;
    private String name;
    private String location;
    private String operatingHours;
    private Integer seatingCapacity;
    private String managerName;
    private String contactNumber;
    private Boolean isVegetarian;
    private Boolean isActive;
}
