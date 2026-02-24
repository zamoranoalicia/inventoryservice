package org.azamorano.inventoryservice.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class ProductPresentation {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Product product;

    @OneToOne
    @Enumerated(EnumType.STRING)
    private PresentationType presentationType;

    private Integer unitsContained;

    @Column(precision = 15, scale = 2)
    private BigDecimal salePriceNet;

    @Column(precision = 15, scale = 2)
    private BigDecimal taxPercentage;

    @Column(precision = 15, scale = 2)
    private BigDecimal salePriceGross;

    private Boolean active;


}
