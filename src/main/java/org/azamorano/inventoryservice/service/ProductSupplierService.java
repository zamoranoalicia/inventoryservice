package org.azamorano.inventoryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.azamorano.inventoryservice.entity.ProductSupplier;
import org.azamorano.inventoryservice.repository.ProductSupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ProductSupplierService {

    private final ProductSupplierRepository repository;

    public ProductSupplierService(ProductSupplierRepository repository) {
        this.repository = repository;
    }

    public ProductSupplier create(ProductSupplier productSupplier) {
        validateProductSupplier(productSupplier);
        log.info("Creating product supplier: {}", productSupplier.getName());
        return repository.save(productSupplier);
    }

    public Optional<ProductSupplier> getById(UUID id) {
        log.debug("Fetching product supplier by ID: {}", id);
        return repository.findById(id);
    }

    public ProductSupplier getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product supplier not found with id: " + id));
    }

    public List<ProductSupplier> getAll() {
        log.debug("Fetching all product suppliers");
        return repository.findAll();
    }

    public ProductSupplier update(UUID id, ProductSupplier supplierDetails) {
        ProductSupplier productSupplier = getByIdOrThrow(id);
        
        if (supplierDetails.getName() != null) {
            productSupplier.setName(supplierDetails.getName());
        }
        if (supplierDetails.getTaxId() != null) {
            productSupplier.setTaxId(supplierDetails.getTaxId());
        }
        if (supplierDetails.getAddress() != null) {
            productSupplier.setAddress(supplierDetails.getAddress());
        }
        if (supplierDetails.getPhone() != null) {
            productSupplier.setPhone(supplierDetails.getPhone());
        }
        if (supplierDetails.getEmail() != null) {
            productSupplier.setEmail(supplierDetails.getEmail());
        }
        if (supplierDetails.getSanitaryAuthorizationNumber() != null) {
            productSupplier.setSanitaryAuthorizationNumber(supplierDetails.getSanitaryAuthorizationNumber());
        }

        validateProductSupplier(productSupplier);
        log.info("Updating product supplier: {}", id);
        return repository.save(productSupplier);
    }

    public void delete(UUID id) {
        ProductSupplier productSupplier = getByIdOrThrow(id);
        log.info("Deleting product supplier: {}", id);
        repository.delete(productSupplier);
    }

    private void validateProductSupplier(ProductSupplier productSupplier) {
        if (productSupplier.getName() == null || productSupplier.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier name cannot be null or empty");
        }
    }
}

