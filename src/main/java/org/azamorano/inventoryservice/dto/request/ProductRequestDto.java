package org.azamorano.inventoryservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "SKU cannot be blank")
    private String sku;

    @NotBlank(message = "Bar code cannot be blank")
    private String barCode;

    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    private String productDescription;

    @NotNull(message = "Category cannot be null")
    private String category;

    @NotNull(message = "Prescription required flag cannot be null")
    private Boolean prescriptionRequired;

    @NotNull(message = "Controlled substance flag cannot be null")
    private Boolean controlledSubstance;

    private UUID laboratoryId;

    private UUID brandId;

    private String sanitaryRegistration;

    @Positive(message = "Reorder level must be positive")
    private Integer reorderLevel;
}

