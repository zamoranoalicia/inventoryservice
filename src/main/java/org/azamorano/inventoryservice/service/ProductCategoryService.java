package org.azamorano.inventoryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.azamorano.inventoryservice.entity.Price;
import org.azamorano.inventoryservice.entity.ProductCategory;
import org.azamorano.inventoryservice.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategory getProductCategoryById(UUID id) {
        return productCategoryRepository.findById(id).orElse(null);
    }

    public ProductCategory createProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public ProductCategory getProductCategoryByName(String name) {
        return productCategoryRepository.findByName(name).orElse(null);
    }
}
