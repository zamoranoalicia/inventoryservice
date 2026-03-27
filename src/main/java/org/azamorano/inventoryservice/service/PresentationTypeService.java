package org.azamorano.inventoryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.azamorano.inventoryservice.entity.PresentationType;
import org.azamorano.inventoryservice.repository.PresentationTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class PresentationTypeService {

    private final PresentationTypeRepository repository;

    public PresentationTypeService(PresentationTypeRepository repository) {
        this.repository = repository;
    }

    public PresentationType create(PresentationType presentationType) {
        validatePresentationType(presentationType);
        log.info("Creating presentation type: {}", presentationType.getName());
        return repository.save(presentationType);
    }

    public Optional<PresentationType> getById(UUID id) {
        log.debug("Fetching presentation type by ID: {}", id);
        return repository.findById(id);
    }

    public PresentationType getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Presentation type not found with id: " + id));
    }

    public List<PresentationType> getAll() {
        log.debug("Fetching all presentation types");
        return repository.findAll();
    }

    public PresentationType update(UUID id, PresentationType presentationTypeDetails) {
        PresentationType presentationType = getByIdOrThrow(id);
        
        if (presentationTypeDetails.getName() != null) {
            presentationType.setName(presentationTypeDetails.getName());
        }
        if (presentationTypeDetails.getDescription() != null) {
            presentationType.setDescription(presentationTypeDetails.getDescription());
        }

        validatePresentationType(presentationType);
        log.info("Updating presentation type: {}", id);
        return repository.save(presentationType);
    }

    public void delete(UUID id) {
        PresentationType presentationType = getByIdOrThrow(id);
        log.info("Deleting presentation type: {}", id);
        repository.delete(presentationType);
    }

    private void validatePresentationType(PresentationType presentationType) {
        if (presentationType.getName() == null || presentationType.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Presentation type name cannot be null or empty");
        }
    }
}

