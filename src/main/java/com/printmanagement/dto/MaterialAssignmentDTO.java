package com.printmanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MaterialAssignmentDTO {
    private Long id;
    
    @NotNull(message = "Order number is required")
    @Positive(message = "Order number must be positive")
    private Integer orderNumber;
    
    @NotNull(message = "Assigned weight is required")
    @Positive(message = "Assigned weight must be positive")
    private Double assignedWeight;
    
    @NotNull(message = "Material ID is required")
    private Long materialId;
    
    private Long routeSheetId;
}