package org.azamorano.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponseDto {

    private UUID id;
    private BigDecimal amount;
    private String currency;
}

