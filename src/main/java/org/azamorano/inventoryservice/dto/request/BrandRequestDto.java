package org.azamorano.inventoryservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequestDto {

    @NotBlank(message = "Brand name cannot be blank")
    private String name;
}

