package com.printmanagement.service;

import com.printmanagement.dto.WeightDTO;
import com.printmanagement.model.Weight;
import com.printmanagement.repository.WeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeightService {
    private final WeightRepository weightRepository;

    @Transactional
    public WeightDTO createWeight(WeightDTO dto) {
        Weight weight = new Weight();
        weight.setName(dto.getName());
        Weight saved = weightRepository.save(weight);
        return convertToDTO(saved);
    }

    @Transactional
    public WeightDTO updateWeight(Long id, WeightDTO dto) {
        Weight weight = weightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Weight not found"));
        weight.setName(dto.getName());
        Weight saved = weightRepository.save(weight);
        return convertToDTO(saved);
    }

    public WeightDTO getWeight(Long id) {
        Weight weight = weightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Weight not found"));
        return convertToDTO(weight);
    }

    public List<WeightDTO> getAllWeights() {
        return weightRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private WeightDTO convertToDTO(Weight weight) {
        WeightDTO dto = new WeightDTO();
        dto.setId(weight.getId());
        dto.setName(weight.getName());
        return dto;
    }
}