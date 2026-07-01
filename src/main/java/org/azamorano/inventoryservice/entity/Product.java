package org.azamorano.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    @ManyToOne
    @JoinColumn(name="category_id")
    private ProductCategory productCategory;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPresentation> productPresentations;

    @Column(nullable = false)
    private boolean prescriptionRequired;

    @Column(nullable = false)
    private boolean controlledSubstance;

    @ManyToOne
    @JoinColumn(name = "laboratory_id")
    private Laboratory laboratory;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryBatch> inventoryBatches;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> prices;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TherapeuticAction> therapeuticActions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActiveIngredient> activeIngredients;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(unique = true)
    private String sanitaryRegistration;

    @Column(nullable = false)
    private int reorderLevel;
}
