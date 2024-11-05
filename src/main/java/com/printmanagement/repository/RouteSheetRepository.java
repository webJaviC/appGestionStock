package com.printmanagement.repository;

import com.printmanagement.model.RouteSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteSheetRepository extends JpaRepository<RouteSheet, Long> {
    Optional<RouteSheet> findByRouteNumber(String routeNumber);
    boolean existsByRouteNumber(String routeNumber);
}