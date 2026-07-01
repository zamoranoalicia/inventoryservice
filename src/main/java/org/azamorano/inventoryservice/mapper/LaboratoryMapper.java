package org.azamorano.inventoryservice.mapper;

import org.azamorano.inventoryservice.dto.request.LaboratoryRequestDto;
import org.azamorano.inventoryservice.dto.response.LaboratoryResponseDto;
import org.azamorano.inventoryservice.entity.Laboratory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LaboratoryMapper {

    public Laboratory toEntity(LaboratoryRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        Laboratory laboratory = new Laboratory();
        laboratory.setName(requestDto.getName());
        laboratory.setCountry(requestDto.getCountry());
        return laboratory;
    }

    public LaboratoryResponseDto toResponseDto(Laboratory entity) {
        if (entity == null) {
            return null;
        }
        LaboratoryResponseDto responseDto = new LaboratoryResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setName(entity.getName());
        responseDto.setCountry(entity.getCountry());
        return responseDto;
    }

    public List<LaboratoryResponseDto> toResponseDtoList(List<Laboratory> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Laboratory toEntity(String laboratoryName) {
        if (laboratoryName == null) {
            return null;
        }
        return Laboratory.builder().name(laboratoryName).build();
    }
}

