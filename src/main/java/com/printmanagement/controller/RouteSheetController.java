package com.printmanagement.controller;

import com.printmanagement.dto.MaterialAssignmentDTO;
import com.printmanagement.dto.RouteSheetDTO;
import com.printmanagement.service.RouteSheetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route-sheets")
@RequiredArgsConstructor
public class RouteSheetController {
    private final RouteSheetService routeSheetService;

    @PostMapping
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<RouteSheetDTO> createRouteSheet(@Valid @RequestBody RouteSheetDTO dto) {
        return ResponseEntity.ok(routeSheetService.createRouteSheet(dto));
    }

    @PutMapping("/{routeNumber}")
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<RouteSheetDTO> updateRouteSheet(
            @PathVariable String routeNumber,
            @Valid @RequestBody RouteSheetDTO dto) {
        return ResponseEntity.ok(routeSheetService.updateRouteSheet(routeNumber, dto));
    }

    @PostMapping("/{routeNumber}/materials")
    @PreAuthorize("hasRole('WAREHOUSE')")
    public ResponseEntity<RouteSheetDTO> assignMaterial(
            @PathVariable String routeNumber,
            @Valid @RequestBody MaterialAssignmentDTO dto) {
        return ResponseEntity.ok(routeSheetService.assignMaterial(routeNumber, dto));
    }

    @PostMapping("/{routeNumber}/close")
    @PreAuthorize("hasRole('WAREHOUSE')")
    public ResponseEntity<RouteSheetDTO> closeRouteSheet(@PathVariable String routeNumber) {
        return ResponseEntity.ok(routeSheetService.closeRouteSheet(routeNumber));
    }

    @GetMapping("/{routeNumber}")
    @PreAuthorize("hasAnyRole('PRODUCTION', 'WAREHOUSE')")
    public ResponseEntity<RouteSheetDTO> getRouteSheet(@PathVariable String routeNumber) {
        return ResponseEntity.ok(routeSheetService.getRouteSheet(routeNumber));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PRODUCTION', 'WAREHOUSE')")
    public ResponseEntity<List<RouteSheetDTO>> getAllRouteSheets() {
        return ResponseEntity.ok(routeSheetService.getAllRouteSheets());
    }
}