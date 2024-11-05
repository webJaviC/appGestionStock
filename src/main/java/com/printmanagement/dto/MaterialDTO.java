package com.printmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MaterialDTO {
    private Long id;
    
    @NotBlank(message = "Code is required")
    private String code;
    
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;
    
    @NotNull(message = "Net weight is required")
    @Positive(message = "Net weight must be positive")
    private Double netWeight;
    
    @NotNull(message = "Width is required")
    @Positive(message = "Width must be positive")
    private Double width;
    
    @NotNull(message = "Length is required")
    @Positive(message = "Length must be positive")
    private Double length;
    
    private Long qualityId;
    private Long weightTypeId;
    private Long receiptId;
}