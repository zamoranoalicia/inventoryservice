package org.azamorano.inventoryservice.mapper;

import org.azamorano.inventoryservice.dto.request.PriceRequestDto;
import org.azamorano.inventoryservice.dto.response.PriceResponseDto;
import org.azamorano.inventoryservice.entity.Price;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceMapper {

    public Price toEntity(PriceRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        Price price = new Price();
        price.setAmount(requestDto.getAmount());
        price.setCurrency(requestDto.getCurrency());
        return price;
    }

    public PriceResponseDto toResponseDto(Price entity) {
        if (entity == null) {
            return null;
        }
        PriceResponseDto responseDto = new PriceResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setAmount(entity.getAmount());
        responseDto.setCurrency(entity.getCurrency());
        return responseDto;
    }

    public List<PriceResponseDto> toResponseDtoList(List<Price> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}

