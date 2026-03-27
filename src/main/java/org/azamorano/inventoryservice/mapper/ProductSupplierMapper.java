package org.azamorano.inventoryservice.mapper;

import org.azamorano.inventoryservice.dto.request.ProductSupplierRequestDto;
import org.azamorano.inventoryservice.dto.response.ProductSupplierResponseDto;
import org.azamorano.inventoryservice.entity.ProductSupplier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductSupplierMapper {

    public ProductSupplier toEntity(ProductSupplierRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        ProductSupplier supplier = new ProductSupplier();
        supplier.setName(requestDto.getName());
        supplier.setTaxId(requestDto.getTaxId());
        supplier.setAddress(requestDto.getAddress());
        supplier.setPhone(requestDto.getPhone());
        supplier.setEmail(requestDto.getEmail());
        supplier.setSanitaryAuthorizationNumber(requestDto.getSanitaryAuthorizationNumber());
        return supplier;
    }

    public ProductSupplierResponseDto toResponseDto(ProductSupplier entity) {
        if (entity == null) {
            return null;
        }
        ProductSupplierResponseDto responseDto = new ProductSupplierResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setName(entity.getName());
        responseDto.setTaxId(entity.getTaxId());
        responseDto.setAddress(entity.getAddress());
        responseDto.setPhone(entity.getPhone());
        responseDto.setEmail(entity.getEmail());
        responseDto.setSanitaryAuthorizationNumber(entity.getSanitaryAuthorizationNumber());
        return responseDto;
    }

    public List<ProductSupplierResponseDto> toResponseDtoList(List<ProductSupplier> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}

