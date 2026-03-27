package org.azamorano.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresentationTypeResponseDto {

    private UUID id;
    private String name;
    private String description;
}

