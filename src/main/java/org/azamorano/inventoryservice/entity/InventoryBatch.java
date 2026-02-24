package org.azamorano.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table
public class InventoryBatch {

    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne
    ProductSupplier supplier;

    String lotNumber;

    ZonedDateTime receptionDate;

    ZonedDateTime expirationDate;

    Integer quantity;

    int alertBeforeDays;

    BigDecimal purchasePrice;
}