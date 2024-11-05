package com.printmanagement.service;

import com.printmanagement.dto.QualityDTO;
import com.printmanagement.model.Quality;
import com.printmanagement.repository.QualityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QualityService {
    private final QualityRepository qualityRepository;

    @Transactional
    public QualityDTO createQuality(QualityDTO dto) {
        Quality quality = new Quality();
        quality.setName(dto.getName());
        Quality saved = qualityRepository.save(quality);
        return convertToDTO(saved);
    }

    @Transactional
    public QualityDTO updateQuality(Long id, QualityDTO dto) {
        Quality quality = qualityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quality not found"));
        quality.setName(dto.getName());
        Quality saved = qualityRepository.save(quality);
        return convertToDTO(saved);
    }

    public QualityDTO getQuality(Long id) {
        Quality quality = qualityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quality not found"));
        return convertToDTO(quality);
    }

    public List<QualityDTO> getAllQualities() {
        return qualityRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private QualityDTO convertToDTO(Quality quality) {
        QualityDTO dto = new QualityDTO();
        dto.setId(quality.getId());
        dto.setName(quality.getName());
        return dto;
    }
}