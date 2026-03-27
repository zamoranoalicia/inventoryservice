package org.azamorano.inventoryservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TherapeuticActionRequestDto {

    @NotBlank(message = "Action cannot be blank")
    private String action;

    private String description;
}

