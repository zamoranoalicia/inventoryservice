package org.azamorano.inventoryservice.mapper;

import org.azamorano.inventoryservice.dto.request.ProductRequestDto;
import org.azamorano.inventoryservice.dto.response.ProductResponseDto;
import org.azamorano.inventoryservice.entity.Product;
import org.azamorano.inventoryservice.entity.ProductCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        Product product = new Product();
        product.setSku(requestDto.getSku());
        product.setBarCode(requestDto.getBarCode());
        product.setProductName(requestDto.getProductName());
        product.setProductDescription(requestDto.getProductDescription());
        if (requestDto.getCategory() != null) {
            product.setCategory(ProductCategory.valueOf(requestDto.getCategory()));
        }
        product.setPrescriptionRequired(requestDto.getPrescriptionRequired() != null && requestDto.getPrescriptionRequired());
        product.setControlledSubstance(requestDto.getControlledSubstance() != null && requestDto.getControlledSubstance());
        product.setSanitaryRegistration(requestDto.getSanitaryRegistration());
        product.setReorderLevel(requestDto.getReorderLevel() != null ? requestDto.getReorderLevel() : 0);
        return product;
    }

    public ProductResponseDto toResponseDto(Product entity) {
        if (entity == null) {
            return null;
        }
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setSku(entity.getSku());
        responseDto.setBarCode(entity.getBarCode());
        responseDto.setProductName(entity.getProductName());
        responseDto.setProductDescription(entity.getProductDescription());
        responseDto.setCategory(entity.getCategory() != null ? entity.getCategory().name() : null);
        responseDto.setPrescriptionRequired(entity.isPrescriptionRequired());
        responseDto.setControlledSubstance(entity.isControlledSubstance());
        responseDto.setLaboratoryId(entity.getLaboratory() != null ? entity.getLaboratory().getId() : null);
        responseDto.setBrandId(entity.getBrand() != null ? entity.getBrand().getId() : null);
        responseDto.setSanitaryRegistration(entity.getSanitaryRegistration());
        responseDto.setReorderLevel(entity.getReorderLevel());
        return responseDto;
    }

    public List<ProductResponseDto> toResponseDtoList(List<Product> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}

