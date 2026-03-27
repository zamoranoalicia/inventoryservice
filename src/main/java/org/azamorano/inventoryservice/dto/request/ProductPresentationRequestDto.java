package org.azamorano.inventoryservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPresentationRequestDto {

    @NotNull(message = "Product ID cannot be null")
    private UUID productId;

    private UUID presentationTypeId;

    @Positive(message = "Units contained must be positive")
    private Integer unitsContained;

    @Positive(message = "Sale price net must be positive")
    private BigDecimal salePriceNet;

    @Positive(message = "Tax percentage must be positive")
    private BigDecimal taxPercentage;

    @Positive(message = "Sale price gross must be positive")
    private BigDecimal salePriceGross;

    private Boolean active;
}

