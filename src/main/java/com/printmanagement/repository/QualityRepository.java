package com.printmanagement.repository;

import com.printmanagement.model.Quality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QualityRepository extends JpaRepository<Quality, Long> {
    Optional<Quality> findByName(String name);
}