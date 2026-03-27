package org.azamorano.inventoryservice.mapper;

import org.azamorano.inventoryservice.dto.request.TherapeuticActionRequestDto;
import org.azamorano.inventoryservice.dto.response.TherapeuticActionResponseDto;
import org.azamorano.inventoryservice.entity.TherapeuticAction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TherapeuticActionMapper {

    public TherapeuticAction toEntity(TherapeuticActionRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        TherapeuticAction action = new TherapeuticAction();
        action.setAction(requestDto.getAction());
        action.setDescription(requestDto.getDescription());
        return action;
    }

    public TherapeuticActionResponseDto toResponseDto(TherapeuticAction entity) {
        if (entity == null) {
            return null;
        }
        TherapeuticActionResponseDto responseDto = new TherapeuticActionResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setAction(entity.getAction());
        responseDto.setDescription(entity.getDescription());
        return responseDto;
    }

    public List<TherapeuticActionResponseDto> toResponseDtoList(List<TherapeuticAction> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}

