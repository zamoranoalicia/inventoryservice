package org.azamorano.inventoryservice.mapper;

import jakarta.validation.constraints.NotNull;
import org.azamorano.inventoryservice.entity.ProductCategory;
import org.azamorano.inventoryservice.service.ProductCategoryService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductCategoryMapper {

    public ProductCategory toEntity(@NotNull(message = "Category cannot be null") String category) {
       return ProductCategory.builder().name(category).build();
    }
}
