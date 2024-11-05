package com.printmanagement.service;

import com.printmanagement.dto.MaterialDTO;
import com.printmanagement.model.Material;
import com.printmanagement.model.Quality;
import com.printmanagement.model.Receipt;
import com.printmanagement.model.Weight;
import com.printmanagement.repository.MaterialRepository;
import com.printmanagement.repository.QualityRepository;
import com.printmanagement.repository.ReceiptRepository;
import com.printmanagement.repository.WeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final QualityRepository qualityRepository;
    private final WeightRepository weightRepository;
    private final ReceiptRepository receiptRepository;

    @Transactional
    public MaterialDTO createMaterial(MaterialDTO dto) {
        Material material = new Material();
        updateMaterialFromDTO(material, dto);
        Material saved = materialRepository.save(material);
        return convertToDTO(saved);
    }

    @Transactional
    public MaterialDTO updateMaterial(Long id, MaterialDTO dto) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        updateMaterialFromDTO(material, dto);
        Material saved = materialRepository.save(material);
        return convertToDTO(saved);
    }

    public MaterialDTO getMaterial(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        return convertToDTO(material);
    }

    public List<MaterialDTO> getAllMaterials() {
        return materialRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MaterialDTO> getAvailableMaterials() {
        return materialRepository.findAvailableMaterials().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void updateMaterialFromDTO(Material material, MaterialDTO dto) {
        material.setCode(dto.getCode());
        material.setWeight(dto.getWeight());
        material.setNetWeight(dto.getNetWeight());
        material.setWidth(dto.getWidth());
        material.setLength(dto.getLength());

        if (dto.getQualityId() != null) {
            Quality quality = qualityRepository.findById(dto.getQualityId())
                    .orElseThrow(() -> new RuntimeException("Quality not found"));
            material.setQuality(quality);
        }

        if (dto.getWeightTypeId() != null) {
            Weight weight = weightRepository.findById(dto.getWeightTypeId())
                    .orElseThrow(() -> new RuntimeException("Weight type not found"));
            material.setWeightType(weight);
        }

        if (dto.getReceiptId() != null) {
            Receipt receipt = receiptRepository.findById(dto.getReceiptId())
                    .orElseThrow(() -> new RuntimeException("Receipt not found"));
            material.setReceipt(receipt);
        }
    }

    private MaterialDTO convertToDTO(Material material) {
        MaterialDTO dto = new MaterialDTO();
        dto.setId(material.getId());
        dto.setCode(material.getCode());
        dto.setWeight(material.getWeight());
        dto.setNetWeight(material.getNetWeight());
        dto.setWidth(material.getWidth());
        dto.setLength(material.getLength());
        dto.setQualityId(material.getQuality() != null ? material.getQuality().getId() : null);
        dto.setWeightTypeId(material.getWeightType() != null ? material.getWeightType().getId() : null);
        dto.setReceiptId(material.getReceipt() != null ? material.getReceipt().getId() : null);
        return dto;
    }
}