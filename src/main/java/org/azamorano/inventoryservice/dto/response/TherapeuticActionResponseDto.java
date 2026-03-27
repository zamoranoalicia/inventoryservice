package org.azamorano.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TherapeuticActionResponseDto {

    private UUID id;
    private String action;
    private String description;
}

