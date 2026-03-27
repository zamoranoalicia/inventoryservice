package org.azamorano.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_batches")
public class InventoryBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private ProductSupplier supplier;

    @Column(name = "lot_number")
    private String lotNumber;

    @Column(name = "reception_date")
    private ZonedDateTime receptionDate;

    @Column(name = "expiration_date")
    private ZonedDateTime expirationDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "alert_before_days")
    private Integer alertBeforeDays;

    @Column(name = "purchase_price", precision = 15, scale = 2)
    private BigDecimal purchasePrice;
}