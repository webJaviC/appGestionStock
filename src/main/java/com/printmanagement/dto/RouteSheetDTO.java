package com.printmanagement.dto;

import com.printmanagement.model.RouteSheetStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RouteSheetDTO {
    private Long id;
    
    @NotBlank(message = "Customer number is required")
    private String customerNumber;
    
    @NotBlank(message = "Product number is required")
    private String productNumber;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;
    
    @NotNull(message = "Length is required")
    @Positive(message = "Length must be positive")
    private Double length;
    
    @NotNull(message = "Width is required")
    @Positive(message = "Width must be positive")
    private Double width;
    
    private LocalDate date;
    private RouteSheetStatus status;
    private Long qualityId;
    private List<MaterialAssignmentDTO> materialAssignments;
    private String routeNumber;
}