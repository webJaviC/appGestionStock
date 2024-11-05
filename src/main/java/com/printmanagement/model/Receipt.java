package com.printmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String receiptNumber;
    private LocalDate date;
    
    @ManyToOne
    private Supplier supplier;
    
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<Material> materials = new ArrayList<>();
}