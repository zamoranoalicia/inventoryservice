package org.azamorano.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplierResponseDto {

    private UUID id;
    private String name;
    private String taxId;
    private String address;
    private String phone;
    private String email;
    private String sanitaryAuthorizationNumber;
}

