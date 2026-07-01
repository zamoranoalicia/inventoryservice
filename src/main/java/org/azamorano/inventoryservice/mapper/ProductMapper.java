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
    private final BrandMapper brandMapper;
    private final LaboratoryMapper laboratoryMapper;
    private final ProductCategoryMapper productCategoryMapper;

    public ProductMapper(BrandMapper brandMapper, LaboratoryMapper laboratoryMapper, ProductCategoryMapper productCategoryMapper) {
        this.brandMapper = brandMapper;
        this.laboratoryMapper = laboratoryMapper;
        this.productCategoryMapper = productCategoryMapper;
    }

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
            product.setProductCategory(productCategoryMapper.toEntity(requestDto.getCategory()));
        }

        if(requestDto.getLaboratory() != null) {
            product.setLaboratory(laboratoryMapper.toEntity(requestDto.getLaboratory()));
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
        responseDto.setCategory(entity.getProductCategory() != null ? entity.getProductCategory().getName() : null);
        responseDto.setPrescriptionRequired(entity.isPrescriptionRequired());
        responseDto.setControlledSubstance(entity.isControlledSubstance());
        responseDto.setLaboratory(entity.getLaboratory() != null ? entity.getLaboratory().getName() : null);
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

