package com.printmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QualityDTO {
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
}