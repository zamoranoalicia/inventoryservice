package org.azamorano.inventoryservice.mapper;

import org.azamorano.inventoryservice.dto.request.ActiveIngredientRequestDto;
import org.azamorano.inventoryservice.dto.response.ActiveIngredientResponseDto;
import org.azamorano.inventoryservice.entity.ActiveIngredient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActiveIngredientMapper {

    public ActiveIngredient toEntity(ActiveIngredientRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        ActiveIngredient ingredient = new ActiveIngredient();
        ingredient.setIngredientName(requestDto.getIngredientName());
        ingredient.setDescription(requestDto.getDescription());
        return ingredient;
    }

    public ActiveIngredientResponseDto toResponseDto(ActiveIngredient entity) {
        if (entity == null) {
            return null;
        }
        ActiveIngredientResponseDto responseDto = new ActiveIngredientResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setIngredientName(entity.getIngredientName());
        responseDto.setDescription(entity.getDescription());
        return responseDto;
    }

    public List<ActiveIngredientResponseDto> toResponseDtoList(List<ActiveIngredient> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}

