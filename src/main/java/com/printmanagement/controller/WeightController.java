package com.printmanagement.controller;

import com.printmanagement.dto.WeightDTO;
import com.printmanagement.service.WeightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weights")
@RequiredArgsConstructor
public class WeightController {
    private final WeightService weightService;

    @PostMapping
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<WeightDTO> createWeight(@Valid @RequestBody WeightDTO dto) {
        return ResponseEntity.ok(weightService.createWeight(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<WeightDTO> updateWeight(
            @PathVariable Long id,
            @Valid @RequestBody WeightDTO dto) {
        return ResponseEntity.ok(weightService.updateWeight(id, dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRODUCTION', 'WAREHOUSE')")
    public ResponseEntity<WeightDTO> getWeight(@PathVariable Long id) {
        return ResponseEntity.ok(weightService.getWeight(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PRODUCTION', 'WAREHOUSE')")
    public ResponseEntity<List<WeightDTO>> getAllWeights() {
        return ResponseEntity.ok(weightService.getAllWeights());
    }
}