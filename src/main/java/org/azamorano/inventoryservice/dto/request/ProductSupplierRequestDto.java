package org.azamorano.inventoryservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplierRequestDto {

    @NotBlank(message = "Supplier name cannot be blank")
    private String name;

    private String taxId;

    private String address;

    private String phone;

    private String email;

    private String sanitaryAuthorizationNumber;
}

