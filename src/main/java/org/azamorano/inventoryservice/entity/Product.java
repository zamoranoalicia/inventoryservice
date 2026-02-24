package org.azamorano.inventoryservice.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue
    UUID id;

    @Column(unique = true, nullable = false)
    private String sku;

    String barCode;

    String productName;

    String productDescription;

    @Enumerated(EnumType.STRING)
    ProductCategory category;

    @OneToMany
    ProductPresentation productPresentation;

    private boolean prescriptionRequired;

    private boolean controlledSubstance;

    @ManyToOne
    private Laboratory laboratory;

    @OneToMany
    List<InventoryBatch> inventoryBatches;

    List<Price> prices;

    @OneToMany
    List<TherapeuticAction> therapeuticActions;

    @OneToMany
    List<ActiveIngredient> activeIngredients;

    @ManyToOne
    Brand brand;

    String sanitaryRegistration;

    int reoderLevel;
}
