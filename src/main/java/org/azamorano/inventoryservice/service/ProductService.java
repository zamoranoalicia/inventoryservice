package org.azamorano.inventoryservice.service;

import org.azamorano.inventoryservice.entity.Brand;
import org.azamorano.inventoryservice.entity.Product;
import org.azamorano.inventoryservice.repository.BrandRepository;
import org.azamorano.inventoryservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final LaboratoryService laboratoryService;


    public ProductService(ProductRepository productRepository, BrandService brandService, LaboratoryService laboratoryService) {
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.laboratoryService = laboratoryService;
    }

    public Product create(Product product) {
        validateProduct(product);
        
        // Brand and Laboratory are optional
        if (product.getBrand() != null) {
            product.setBrand(brandService.saveBrand(product.getBrand()));
        }
        if (product.getLaboratory() != null) {
            product.setLaboratory(laboratoryService.saveLaboratory(product.getLaboratory()));
        }
        
        return productRepository.save(product);
    }

    public Optional<Product> getById(UUID id) {
        return productRepository.findById(id);
    }

    public Product getByIdOrThrow(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product update(UUID id, Product productDetails) {
        Product product = getByIdOrThrow(id);
        
        if (productDetails.getSku() != null) {
            product.setSku(productDetails.getSku());
        }
        if (productDetails.getBarCode() != null) {
            product.setBarCode(productDetails.getBarCode());
        }
        if (productDetails.getProductName() != null) {
            product.setProductName(productDetails.getProductName());
        }
        if (productDetails.getProductDescription() != null) {
            product.setProductDescription(productDetails.getProductDescription());
        }
        if (productDetails.getCategory() != null) {
            product.setCategory(productDetails.getCategory());
        }
        product.setPrescriptionRequired(productDetails.isPrescriptionRequired());
        product.setControlledSubstance(productDetails.isControlledSubstance());
        if (productDetails.getLaboratory() != null) {
            product.setLaboratory(productDetails.getLaboratory());
        }
        if (productDetails.getBrand() != null) {
            product.setBrand(productDetails.getBrand());
        }
        if (productDetails.getSanitaryRegistration() != null) {
            product.setSanitaryRegistration(productDetails.getSanitaryRegistration());
        }
        if (productDetails.getReorderLevel() > 0) {
            product.setReorderLevel(productDetails.getReorderLevel());
        }

        validateProduct(product);
        return productRepository.save(product);
    }

    public void delete(UUID id) {
        Product product = getByIdOrThrow(id);
        productRepository.delete(product);
    }

    public Optional<Product> findBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    public Optional<Product> findBySanitaryRegistration(String sanitaryRegistration) {
        return productRepository.findBySanitaryRegistration(sanitaryRegistration);
    }

    private void validateProduct(Product product) {
        if (product.getSku() == null || product.getSku().trim().isEmpty()) {
            throw new IllegalArgumentException("SKU cannot be null or empty");
        }
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (product.getReorderLevel() < 0) {
            throw new IllegalArgumentException("Reorder level cannot be negative");
        }
    }
}

