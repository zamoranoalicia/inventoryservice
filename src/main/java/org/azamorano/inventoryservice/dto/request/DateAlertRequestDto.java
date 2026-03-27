package org.azamorano.inventoryservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateAlertRequestDto {

    @NotNull(message = "Expiration date cannot be null")
    private ZonedDateTime expirationDate;

    private ZonedDateTime alertDate;
}

