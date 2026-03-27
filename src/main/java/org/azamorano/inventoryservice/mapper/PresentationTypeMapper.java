package org.azamorano.inventoryservice.mapper;

import org.azamorano.inventoryservice.dto.request.PresentationTypeRequestDto;
import org.azamorano.inventoryservice.dto.response.PresentationTypeResponseDto;
import org.azamorano.inventoryservice.entity.PresentationType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PresentationTypeMapper {

    public PresentationType toEntity(PresentationTypeRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        PresentationType type = new PresentationType();
        type.setName(requestDto.getName());
        type.setDescription(requestDto.getDescription());
        return type;
    }

    public PresentationTypeResponseDto toResponseDto(PresentationType entity) {
        if (entity == null) {
            return null;
        }
        PresentationTypeResponseDto responseDto = new PresentationTypeResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setName(entity.getName());
        responseDto.setDescription(entity.getDescription());
        return responseDto;
    }

    public List<PresentationTypeResponseDto> toResponseDtoList(List<PresentationType> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}

