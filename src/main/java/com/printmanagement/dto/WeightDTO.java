package com.printmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WeightDTO {
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
}