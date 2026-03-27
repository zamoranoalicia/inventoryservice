package org.azamorano.inventoryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.azamorano.inventoryservice.entity.ProductPresentation;
import org.azamorano.inventoryservice.entity.Product;
import org.azamorano.inventoryservice.entity.PresentationType;
import org.azamorano.inventoryservice.repository.ProductPresentationRepository;
import org.azamorano.inventoryservice.repository.ProductRepository;
import org.azamorano.inventoryservice.repository.PresentationTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ProductPresentationService {

    private final ProductPresentationRepository repository;
    private final ProductRepository productRepository;
    private final PresentationTypeRepository presentationTypeRepository;

    public ProductPresentationService(ProductPresentationRepository repository,
                                      ProductRepository productRepository,
                                      PresentationTypeRepository presentationTypeRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.presentationTypeRepository = presentationTypeRepository;
    }

    public ProductPresentation create(ProductPresentation productPresentation) {
        validateProductPresentation(productPresentation);
        log.info("Creating product presentation for product ID: {}", 
                productPresentation.getProduct() != null ? productPresentation.getProduct().getId() : null);
        return repository.save(productPresentation);
    }

    public Optional<ProductPresentation> getById(UUID id) {
        log.debug("Fetching product presentation by ID: {}", id);
        return repository.findById(id);
    }

    public ProductPresentation getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product presentation not found with id: " + id));
    }

    public List<ProductPresentation> getAll() {
        log.debug("Fetching all product presentations");
        return repository.findAll();
    }

    public ProductPresentation update(UUID id, ProductPresentation presentationDetails) {
        ProductPresentation productPresentation = getByIdOrThrow(id);
        
        if (presentationDetails.getProduct() != null) {
            Product product = productRepository.findById(presentationDetails.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + presentationDetails.getProduct().getId()));
            productPresentation.setProduct(product);
        }
        if (presentationDetails.getPresentationType() != null) {
            PresentationType type = presentationTypeRepository.findById(presentationDetails.getPresentationType().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Presentation type not found with id: " + presentationDetails.getPresentationType().getId()));
            productPresentation.setPresentationType(type);
        }
        if (presentationDetails.getUnitsContained() != null) {
            productPresentation.setUnitsContained(presentationDetails.getUnitsContained());
        }
        if (presentationDetails.getSalePriceNet() != null) {
            productPresentation.setSalePriceNet(presentationDetails.getSalePriceNet());
        }
        if (presentationDetails.getTaxPercentage() != null) {
            productPresentation.setTaxPercentage(presentationDetails.getTaxPercentage());
        }
        if (presentationDetails.getSalePriceGross() != null) {
            productPresentation.setSalePriceGross(presentationDetails.getSalePriceGross());
        }
        if (presentationDetails.getActive() != null) {
            productPresentation.setActive(presentationDetails.getActive());
        }

        validateProductPresentation(productPresentation);
        log.info("Updating product presentation: {}", id);
        return repository.save(productPresentation);
    }

    public void delete(UUID id) {
        ProductPresentation productPresentation = getByIdOrThrow(id);
        log.info("Deleting product presentation: {}", id);
        repository.delete(productPresentation);
    }

    private void validateProductPresentation(ProductPresentation productPresentation) {
        if (productPresentation.getProduct() == null) {
            throw new IllegalArgumentException("Product cannot be null in product presentation");
        }
    }
}

