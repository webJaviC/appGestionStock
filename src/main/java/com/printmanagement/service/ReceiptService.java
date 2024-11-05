package com.printmanagement.service;

import com.printmanagement.dto.MaterialDTO;
import com.printmanagement.dto.ReceiptDTO;
import com.printmanagement.model.Material;
import com.printmanagement.model.Receipt;
import com.printmanagement.model.Supplier;
import com.printmanagement.repository.MaterialRepository;
import com.printmanagement.repository.ReceiptRepository;
import com.printmanagement.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final SupplierRepository supplierRepository;
    private final MaterialRepository materialRepository;
    private final MaterialService materialService;

    @Transactional
    public ReceiptDTO createReceipt(ReceiptDTO dto) {
        validateReceiptNumber(dto.getReceiptNumber());
        Receipt receipt = new Receipt();
        updateReceiptFromDTO(receipt, dto);
        Receipt saved = receiptRepository.save(receipt);
        return convertToDTO(saved);
    }

    @Transactional
    public ReceiptDTO updateReceipt(String receiptNumber, ReceiptDTO dto) {
        Receipt receipt = receiptRepository.findByReceiptNumber(receiptNumber)
                .orElseThrow(() -> new RuntimeException("Receipt not found"));
        updateReceiptFromDTO(receipt, dto);
        Receipt saved = receiptRepository.save(receipt);
        return convertToDTO(saved);
    }

    @Transactional
    public ReceiptDTO processReceiptFile(MultipartFile file) {
        try {
            List<String> lines = readLines(file);
            if (lines.isEmpty()) {
                throw new RuntimeException("Empty file");
            }

            // First line contains receipt information
            String[] receiptInfo = lines.get(0).split(",");
            if (receiptInfo.length < 2) {
                throw new RuntimeException("Invalid receipt information format");
            }

            String receiptNumber = receiptInfo[0].trim();
            String supplierCode = receiptInfo[1].trim();

            validateReceiptNumber(receiptNumber);
            
            Supplier supplier = supplierRepository.findByCode(supplierCode)
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));

            Receipt receipt = new Receipt();
            receipt.setReceiptNumber(receiptNumber);
            receipt.setDate(LocalDate.now());
            receipt.setSupplier(supplier);

            // Process materials from remaining lines
            List<Material> materials = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                Material material = processMaterialLine(lines.get(i));
                material.setReceipt(receipt);
                materials.add(material);
            }

            receipt.setMaterials(materials);
            Receipt saved = receiptRepository.save(receipt);
            return convertToDTO(saved);

        } catch (IOException e) {
            throw new RuntimeException("Error processing file: " + e.getMessage());
        }
    }

    public ReceiptDTO getReceipt(String receiptNumber) {
        Receipt receipt = receiptRepository.findByReceiptNumber(receiptNumber)
                .orElseThrow(() -> new RuntimeException("Receipt not found"));
        return convertToDTO(receipt);
    }

    public List<ReceiptDTO> getAllReceipts() {
        return receiptRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void validateReceiptNumber(String receiptNumber) {
        if (receiptRepository.existsByReceiptNumber(receiptNumber)) {
            throw new RuntimeException("Receipt number already exists");
        }
    }

    private List<String> readLines(MultipartFile file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        }
        return lines;
    }

    private Material processMaterialLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 6) {
            throw new RuntimeException("Invalid material format");
        }

        Material material = new Material();
        material.setCode(parts[0].trim());
        material.setWeight(Double.parseDouble(parts[1].trim()));
        material.setNetWeight(Double.parseDouble(parts[2].trim()));
        material.setWidth(Double.parseDouble(parts[3].trim()));
        material.setLength(Double.parseDouble(parts[4].trim()));
        // Additional material properties can be set here

        return material;
    }

    private void updateReceiptFromDTO(Receipt receipt, ReceiptDTO dto) {
        receipt.setReceiptNumber(dto.getReceiptNumber());
        receipt.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());

        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            receipt.setSupplier(supplier);
        }

        if (dto.getMaterials() != null) {
            List<Material> materials = dto.getMaterials().stream()
                    .map(materialDTO -> {
                        Material material = new Material();
                        material.setCode(materialDTO.getCode());
                        material.setWeight(materialDTO.getWeight());
                        material.setNetWeight(materialDTO.getNetWeight());
                        material.setWidth(materialDTO.getWidth());
                        material.setLength(materialDTO.getLength());
                        material.setReceipt(receipt);
                        return material;
                    })
                    .collect(Collectors.toList());
            receipt.setMaterials(materials);
        }
    }

    private ReceiptDTO convertToDTO(Receipt receipt) {
        ReceiptDTO dto = new ReceiptDTO();
        dto.setId(receipt.getId());
        dto.setReceiptNumber(receipt.getReceiptNumber());
        dto.setDate(receipt.getDate());
        dto.setSupplierId(receipt.getSupplier() != null ? receipt.getSupplier().getId() : null);

        if (receipt.getMaterials() != null) {
            List<MaterialDTO> materialDTOs = receipt.getMaterials().stream()
                    .map(material -> {
                        MaterialDTO materialDTO = new MaterialDTO();
                        materialDTO.setId(material.getId());
                        materialDTO.setCode(material.getCode());
                        materialDTO.setWeight(material.getWeight());
                        materialDTO.setNetWeight(material.getNetWeight());
                        materialDTO.setWidth(material.getWidth());
                        materialDTO.setLength(material.getLength());
                        return materialDTO;
                    })
                    .collect(Collectors.toList());
            dto.setMaterials(materialDTOs);
        }

        return dto;
    }
}