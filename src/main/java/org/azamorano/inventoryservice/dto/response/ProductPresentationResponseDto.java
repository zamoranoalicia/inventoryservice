package org.azamorano.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPresentationResponseDto {

    private UUID id;
    private UUID productId;
    private UUID presentationTypeId;
    private Integer unitsContained;
    private BigDecimal salePriceNet;
    private BigDecimal taxPercentage;
    private BigDecimal salePriceGross;
    private Boolean active;
}

