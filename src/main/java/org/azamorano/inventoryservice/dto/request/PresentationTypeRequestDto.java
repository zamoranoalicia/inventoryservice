package org.azamorano.inventoryservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresentationTypeRequestDto {

    @NotBlank(message = "Presentation type name cannot be blank")
    private String name;

    private String description;
}

