package org.azamorano.inventoryservice.mapper;

import org.azamorano.inventoryservice.dto.request.ProductPresentationRequestDto;
import org.azamorano.inventoryservice.dto.response.ProductPresentationResponseDto;
import org.azamorano.inventoryservice.entity.ProductPresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductPresentationMapper {

    public ProductPresentation toEntity(ProductPresentationRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        ProductPresentation presentation = new ProductPresentation();
        presentation.setUnitsContained(requestDto.getUnitsContained());
        presentation.setSalePriceNet(requestDto.getSalePriceNet());
        presentation.setTaxPercentage(requestDto.getTaxPercentage());
        presentation.setSalePriceGross(requestDto.getSalePriceGross());
        presentation.setActive(requestDto.getActive());
        return presentation;
    }

    public ProductPresentationResponseDto toResponseDto(ProductPresentation entity) {
        if (entity == null) {
            return null;
        }
        ProductPresentationResponseDto responseDto = new ProductPresentationResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setProductId(entity.getProduct() != null ? entity.getProduct().getId() : null);
        responseDto.setPresentationTypeId(entity.getPresentationType() != null ? entity.getPresentationType().getId() : null);
        responseDto.setUnitsContained(entity.getUnitsContained());
        responseDto.setSalePriceNet(entity.getSalePriceNet());
        responseDto.setTaxPercentage(entity.getTaxPercentage());
        responseDto.setSalePriceGross(entity.getSalePriceGross());
        responseDto.setActive(entity.getActive());
        return responseDto;
    }

    public List<ProductPresentationResponseDto> toResponseDtoList(List<ProductPresentation> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}

