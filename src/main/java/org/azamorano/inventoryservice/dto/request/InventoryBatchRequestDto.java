package org.azamorano.inventoryservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryBatchRequestDto {

    @NotNull(message = "Product ID cannot be null")
    private UUID productId;

    private UUID supplierId;

    private String lotNumber;

    private ZonedDateTime receptionDate;

    private ZonedDateTime expirationDate;

    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @Positive(message = "Alert before days must be positive")
    private Integer alertBeforeDays;

    @Positive(message = "Purchase price must be positive")
    private BigDecimal purchasePrice;
}

