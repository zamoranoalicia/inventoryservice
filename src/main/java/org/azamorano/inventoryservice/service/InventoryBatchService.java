package org.azamorano.inventoryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.azamorano.inventoryservice.entity.InventoryBatch;
import org.azamorano.inventoryservice.entity.Product;
import org.azamorano.inventoryservice.entity.ProductSupplier;
import org.azamorano.inventoryservice.repository.InventoryBatchRepository;
import org.azamorano.inventoryservice.repository.ProductRepository;
import org.azamorano.inventoryservice.repository.ProductSupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class InventoryBatchService {

    private final InventoryBatchRepository repository;
    private final ProductRepository productRepository;
    private final ProductSupplierRepository supplierRepository;

    public InventoryBatchService(InventoryBatchRepository repository,
                                  ProductRepository productRepository,
                                  ProductSupplierRepository supplierRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    public InventoryBatch create(InventoryBatch inventoryBatch) {
        validateInventoryBatch(inventoryBatch);
        log.info("Creating inventory batch for product ID: {}", 
                inventoryBatch.getProduct() != null ? inventoryBatch.getProduct().getId() : null);
        return repository.save(inventoryBatch);
    }

    public Optional<InventoryBatch> getById(UUID id) {
        log.debug("Fetching inventory batch by ID: {}", id);
        return repository.findById(id);
    }

    public InventoryBatch getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory batch not found with id: " + id));
    }

    public List<InventoryBatch> getAll() {
        log.debug("Fetching all inventory batches");
        return repository.findAll();
    }

    public InventoryBatch update(UUID id, InventoryBatch batchDetails) {
        InventoryBatch inventoryBatch = getByIdOrThrow(id);
        
        if (batchDetails.getProduct() != null) {
            Product product = productRepository.findById(batchDetails.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + batchDetails.getProduct().getId()));
            inventoryBatch.setProduct(product);
        }
        if (batchDetails.getSupplier() != null) {
            ProductSupplier supplier = supplierRepository.findById(batchDetails.getSupplier().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Supplier not found with id: " + batchDetails.getSupplier().getId()));
            inventoryBatch.setSupplier(supplier);
        }
        if (batchDetails.getLotNumber() != null) {
            inventoryBatch.setLotNumber(batchDetails.getLotNumber());
        }
        if (batchDetails.getReceptionDate() != null) {
            inventoryBatch.setReceptionDate(batchDetails.getReceptionDate());
        }
        if (batchDetails.getExpirationDate() != null) {
            inventoryBatch.setExpirationDate(batchDetails.getExpirationDate());
        }
        if (batchDetails.getQuantity() != null) {
            inventoryBatch.setQuantity(batchDetails.getQuantity());
        }
        if (batchDetails.getAlertBeforeDays() != null) {
            inventoryBatch.setAlertBeforeDays(batchDetails.getAlertBeforeDays());
        }
        if (batchDetails.getPurchasePrice() != null) {
            inventoryBatch.setPurchasePrice(batchDetails.getPurchasePrice());
        }

        validateInventoryBatch(inventoryBatch);
        log.info("Updating inventory batch: {}", id);
        return repository.save(inventoryBatch);
    }

    public void delete(UUID id) {
        InventoryBatch inventoryBatch = getByIdOrThrow(id);
        log.info("Deleting inventory batch: {}", id);
        repository.delete(inventoryBatch);
    }

    private void validateInventoryBatch(InventoryBatch inventoryBatch) {
        if (inventoryBatch.getProduct() == null) {
            throw new IllegalArgumentException("Product cannot be null in inventory batch");
        }
        if (inventoryBatch.getQuantity() == null || inventoryBatch.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }
}

