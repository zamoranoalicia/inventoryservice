package org.azamorano.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateAlertResponseDto {

    private UUID id;
    private ZonedDateTime expirationDate;
    private ZonedDateTime alertDate;
}

