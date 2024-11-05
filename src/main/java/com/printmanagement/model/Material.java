package com.printmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String code;
    private Double weight;
    private Double netWeight;
    private Double width;
    private Double length;
    
    @ManyToOne
    private Quality quality;
    
    @ManyToOne
    private Weight weightType;
    
    @ManyToOne
    private Receipt receipt;
}