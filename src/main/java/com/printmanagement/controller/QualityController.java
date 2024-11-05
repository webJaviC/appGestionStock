package com.printmanagement.controller;

import com.printmanagement.dto.QualityDTO;
import com.printmanagement.service.QualityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qualities")
@RequiredArgsConstructor
public class QualityController {
    private final QualityService qualityService;

    @PostMapping
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<QualityDTO> createQuality(@Valid @RequestBody QualityDTO dto) {
        return ResponseEntity.ok(qualityService.createQuality(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<QualityDTO> updateQuality(
            @PathVariable Long id,
            @Valid @RequestBody QualityDTO dto) {
        return ResponseEntity.ok(qualityService.updateQuality(id, dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRODUCTION', 'WAREHOUSE')")
    public ResponseEntity<QualityDTO> getQuality(@PathVariable Long id) {
        return ResponseEntity.ok(qualityService.getQuality(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PRODUCTION', 'WAREHOUSE')")
    public ResponseEntity<List<QualityDTO>> getAllQualities() {
        return ResponseEntity.ok(qualityService.getAllQualities());
    }
}