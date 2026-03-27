package org.azamorano.inventoryservice.mapper;

import org.azamorano.inventoryservice.dto.request.DateAlertRequestDto;
import org.azamorano.inventoryservice.dto.response.DateAlertResponseDto;
import org.azamorano.inventoryservice.entity.DateAlert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DateAlertMapper {

    public DateAlert toEntity(DateAlertRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        DateAlert alert = new DateAlert();
        alert.setExpirationDate(requestDto.getExpirationDate());
        alert.setAlertDate(requestDto.getAlertDate());
        return alert;
    }

    public DateAlertResponseDto toResponseDto(DateAlert entity) {
        if (entity == null) {
            return null;
        }
        DateAlertResponseDto responseDto = new DateAlertResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setExpirationDate(entity.getExpirationDate());
        responseDto.setAlertDate(entity.getAlertDate());
        return responseDto;
    }

    public List<DateAlertResponseDto> toResponseDtoList(List<DateAlert> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}

