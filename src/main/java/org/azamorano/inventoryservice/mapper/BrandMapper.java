package org.azamorano.inventoryservice.mapper;

import org.azamorano.inventoryservice.dto.request.BrandRequestDto;
import org.azamorano.inventoryservice.dto.response.BrandResponseDto;
import org.azamorano.inventoryservice.entity.Brand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BrandMapper {

    public Brand toEntity(BrandRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        Brand brand = new Brand();
        brand.setName(requestDto.getName());
        return brand;
    }

    public BrandResponseDto toResponseDto(Brand entity) {
        if (entity == null) {
            return null;
        }
        BrandResponseDto responseDto = new BrandResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setName(entity.getName());
        return responseDto;
    }

    public List<BrandResponseDto> toResponseDtoList(List<Brand> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}

