package com.printmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "material_assignments")
public class MaterialAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer orderNumber;
    private Double assignedWeight;
    
    @ManyToOne
    private RouteSheet routeSheet;
    
    @ManyToOne
    private Material material;
}