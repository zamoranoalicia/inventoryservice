package org.azamorano.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryBatchResponseDto {

    private UUID id;
    private UUID productId;
    private UUID supplierId;
    private String lotNumber;
    private ZonedDateTime receptionDate;
    private ZonedDateTime expirationDate;
    private Integer quantity;
    private Integer alertBeforeDays;
    private BigDecimal purchasePrice;
}

