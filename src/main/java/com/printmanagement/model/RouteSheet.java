package com.printmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "route_sheets")
public class RouteSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String routeNumber;
    
    private String customerNumber;
    private String productNumber;
    private String description;
    private Double weight;
    private Double length;
    private Double width;
    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    private RouteSheetStatus status;
    
    @ManyToOne
    private Quality quality;
    
    @OneToMany(mappedBy = "routeSheet", cascade = CascadeType.ALL)
    private List<MaterialAssignment> materialAssignments = new ArrayList<>();
}