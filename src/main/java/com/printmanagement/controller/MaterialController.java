package com.printmanagement.controller;

import com.printmanagement.dto.MaterialDTO;
import com.printmanagement.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {
    private final MaterialService materialService;

    @PostMapping
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<MaterialDTO> createMaterial(@Valid @RequestBody MaterialDTO dto) {
        return ResponseEntity.ok(materialService.createMaterial(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<MaterialDTO> updateMaterial(
            @PathVariable Long id,
            @Valid @RequestBody MaterialDTO dto) {
        return ResponseEntity.ok(materialService.updateMaterial(id, dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRODUCTION', 'WAREHOUSE')")
    public ResponseEntity<MaterialDTO> getMaterial(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.getMaterial(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PRODUCTION', 'WAREHOUSE')")
    public ResponseEntity<List<MaterialDTO>> getAllMaterials() {
        return ResponseEntity.ok(materialService.getAllMaterials());
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('PRODUCTION', 'WAREHOUSE')")
    public ResponseEntity<List<MaterialDTO>> getAvailableMaterials() {
        return ResponseEntity.ok(materialService.getAvailableMaterials());
    }
}