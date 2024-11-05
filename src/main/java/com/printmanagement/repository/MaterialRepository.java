package com.printmanagement.repository;

import com.printmanagement.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByCode(String code);
    
    @Query("SELECT m FROM Material m WHERE m.receipt IS NULL")
    List<Material> findAvailableMaterials();
}