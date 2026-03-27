package org.azamorano.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product_presentations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "presentation_type_id")
    private PresentationType presentationType;

    @Column(name = "units_contained")
    private Integer unitsContained;

    @Column(name = "sale_price_net", precision = 15, scale = 2)
    private BigDecimal salePriceNet;

    @Column(name = "tax_percentage", precision = 15, scale = 2)
    private BigDecimal taxPercentage;

    @Column(name = "sale_price_gross", precision = 15, scale = 2)
    private BigDecimal salePriceGross;

    @Column(name = "active")
    private Boolean active;

}
