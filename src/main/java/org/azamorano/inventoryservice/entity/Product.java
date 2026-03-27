package org.azamorano.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private String barCode;

    @Column(nullable = false)
    private String productName;

    @Column(columnDefinition = "TEXT")
    private String productDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Column(nullable = false)
    private boolean prescriptionRequired;

    @Column(nullable = false)
    private boolean controlledSubstance;

    @ManyToOne
    @JoinColumn(name = "laboratory_id")
    private Laboratory laboratory;


    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(unique = true)
    private String sanitaryRegistration;

    @Column(nullable = false)
    private int reorderLevel;
}
