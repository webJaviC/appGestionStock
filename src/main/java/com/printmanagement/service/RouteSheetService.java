package com.printmanagement.service;

import com.printmanagement.dto.MaterialAssignmentDTO;
import com.printmanagement.dto.RouteSheetDTO;
import com.printmanagement.model.*;
import com.printmanagement.repository.MaterialRepository;
import com.printmanagement.repository.QualityRepository;
import com.printmanagement.repository.RouteSheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteSheetService {
    private final RouteSheetRepository routeSheetRepository;
    private final QualityRepository qualityRepository;
    private final MaterialRepository materialRepository;
    private final SequenceGeneratorService sequenceGenerator;

    @Transactional
    public RouteSheetDTO createRouteSheet(RouteSheetDTO dto) {
        RouteSheet routeSheet = new RouteSheet();
        routeSheet.setRouteNumber(sequenceGenerator.generateRouteNumber());
        updateRouteSheetFromDTO(routeSheet, dto);
        routeSheet.setDate(LocalDate.now());
        routeSheet.setStatus(RouteSheetStatus.OPEN);
        
        RouteSheet saved = routeSheetRepository.save(routeSheet);
        return convertToDTO(saved);
    }

    @Transactional
    public RouteSheetDTO updateRouteSheet(String routeNumber, RouteSheetDTO dto) {
        RouteSheet routeSheet = routeSheetRepository.findByRouteNumber(routeNumber)
                .orElseThrow(() -> new RuntimeException("Route sheet not found"));
        
        if (routeSheet.getStatus() == RouteSheetStatus.CLOSED) {
            throw new RuntimeException("Cannot update closed route sheet");
        }
        
        updateRouteSheetFromDTO(routeSheet, dto);
        RouteSheet saved = routeSheetRepository.save(routeSheet);
        return convertToDTO(saved);
    }

    @Transactional
    public RouteSheetDTO assignMaterial(String routeNumber, MaterialAssignmentDTO assignmentDTO) {
        RouteSheet routeSheet = routeSheetRepository.findByRouteNumber(routeNumber)
                .orElseThrow(() -> new RuntimeException("Route sheet not found"));
        
        if (routeSheet.getStatus() == RouteSheetStatus.CLOSED) {
            throw new RuntimeException("Cannot assign materials to closed route sheet");
        }
        
        Material material = materialRepository.findById(assignmentDTO.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));
        
        MaterialAssignment assignment = new MaterialAssignment();
        assignment.setOrderNumber(assignmentDTO.getOrderNumber());
        assignment.setAssignedWeight(assignmentDTO.getAssignedWeight());
        assignment.setMaterial(material);
        assignment.setRouteSheet(routeSheet);
        
        routeSheet.getMaterialAssignments().add(assignment);
        RouteSheet saved = routeSheetRepository.save(routeSheet);
        return convertToDTO(saved);
    }

    @Transactional
    public RouteSheetDTO closeRouteSheet(String routeNumber) {
        RouteSheet routeSheet = routeSheetRepository.findByRouteNumber(routeNumber)
                .orElseThrow(() -> new RuntimeException("Route sheet not found"));
        
        if (routeSheet.getStatus() == RouteSheetStatus.CLOSED) {
            throw new RuntimeException("Route sheet is already closed");
        }
        
        routeSheet.setStatus(RouteSheetStatus.CLOSED);
        RouteSheet saved = routeSheetRepository.save(routeSheet);
        return convertToDTO(saved);
    }

    public RouteSheetDTO getRouteSheet(String routeNumber) {
        RouteSheet routeSheet = routeSheetRepository.findByRouteNumber(routeNumber)
                .orElseThrow(() -> new RuntimeException("Route sheet not found"));
        return convertToDTO(routeSheet);
    }

    public List<RouteSheetDTO> getAllRouteSheets() {
        return routeSheetRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void updateRouteSheetFromDTO(RouteSheet routeSheet, RouteSheetDTO dto) {
        routeSheet.setCustomerNumber(dto.getCustomerNumber());
        routeSheet.setProductNumber(dto.getProductNumber());
        routeSheet.setDescription(dto.getDescription());
        routeSheet.setWeight(dto.getWeight());
        routeSheet.setLength(dto.getLength());
        routeSheet.setWidth(dto.getWidth());
        
        if (dto.getQualityId() != null) {
            Quality quality = qualityRepository.findById(dto.getQualityId())
                    .orElseThrow(() -> new RuntimeException("Quality not found"));
            routeSheet.setQuality(quality);
        }
    }

    private RouteSheetDTO convertToDTO(RouteSheet routeSheet) {
        RouteSheetDTO dto = new RouteSheetDTO();
        dto.setId(routeSheet.getId());
        dto.setRouteNumber(routeSheet.getRouteNumber());
        dto.setCustomerNumber(routeSheet.getCustomerNumber());
        dto.setProductNumber(routeSheet.getProductNumber());
        dto.setDescription(routeSheet.getDescription());
        dto.setWeight(routeSheet.getWeight());
        dto.setLength(routeSheet.getLength());
        dto.setWidth(routeSheet.getWidth());
        dto.setDate(routeSheet.getDate());
        dto.setStatus(routeSheet.getStatus());
        dto.setQualityId(routeSheet.getQuality() != null ? routeSheet.getQuality().getId() : null);
        
        List<MaterialAssignmentDTO> assignments = routeSheet.getMaterialAssignments().stream()
                .map(this::convertToAssignmentDTO)
                .collect(Collectors.toList());
        dto.setMaterialAssignments(assignments);
        
        return dto;
    }

    private MaterialAssignmentDTO convertToAssignmentDTO(MaterialAssignment assignment) {
        MaterialAssignmentDTO dto = new MaterialAssignmentDTO();
        dto.setId(assignment.getId());
        dto.setOrderNumber(assignment.getOrderNumber());
        dto.setAssignedWeight(assignment.getAssignedWeight());
        dto.setMaterialId(assignment.getMaterial().getId());
        dto.setRouteSheetId(assignment.getRouteSheet().getId());
        return dto;
    }
}