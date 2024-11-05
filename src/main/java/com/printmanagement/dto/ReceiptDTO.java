package com.printmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReceiptDTO {
    private Long id;
    
    @NotBlank(message = "Receipt number is required")
    private String receiptNumber;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @NotNull(message = "Supplier is required")
    private Long supplierId;
    
    private List<MaterialDTO> materials;
}