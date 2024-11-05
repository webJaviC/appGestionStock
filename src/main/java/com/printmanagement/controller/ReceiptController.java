package com.printmanagement.controller;

import com.printmanagement.dto.ReceiptDTO;
import com.printmanagement.service.ReceiptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/receipts")
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;

    @PostMapping
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<ReceiptDTO> createReceipt(@Valid @RequestBody ReceiptDTO dto) {
        return ResponseEntity.ok(receiptService.createReceipt(dto));
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<ReceiptDTO> uploadReceipt(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(receiptService.processReceiptFile(file));
    }

    @PutMapping("/{receiptNumber}")
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<ReceiptDTO> updateReceipt(
            @PathVariable String receiptNumber,
            @Valid @RequestBody ReceiptDTO dto) {
        return ResponseEntity.ok(receiptService.updateReceipt(receiptNumber, dto));
    }

    @GetMapping("/{receiptNumber}")
    @PreAuthorize("hasAnyRole('PRODUCTION', 'WAREHOUSE')")
    public ResponseEntity<ReceiptDTO> getReceipt(@PathVariable String receiptNumber) {
        return ResponseEntity.ok(receiptService.getReceipt(receiptNumber));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PRODUCTION', 'WAREHOUSE')")
    public ResponseEntity<List<ReceiptDTO>> getAllReceipts() {
        return ResponseEntity.ok(receiptService.getAllReceipts());
    }
}