package org.azamorano.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private UUID id;
    private String sku;
    private String barCode;
    private String productName;
    private String productDescription;
    private String category;
    private Boolean prescriptionRequired;
    private Boolean controlledSubstance;
    private UUID laboratoryId;
    private UUID brandId;
    private String sanitaryRegistration;
    private Integer reorderLevel;
}

