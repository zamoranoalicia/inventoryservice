package org.azamorano.inventoryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.azamorano.inventoryservice.entity.ActiveIngredient;
import org.azamorano.inventoryservice.repository.ActiveIngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ActiveIngredientService {

    private final ActiveIngredientRepository repository;

    public ActiveIngredientService(ActiveIngredientRepository repository) {
        this.repository = repository;
    }

    public ActiveIngredient create(ActiveIngredient activeIngredient) {
        validateActiveIngredient(activeIngredient);
        log.info("Creating active ingredient: {}", activeIngredient.getIngredientName());
        return repository.save(activeIngredient);
    }

    public Optional<ActiveIngredient> getById(UUID id) {
        log.debug("Fetching active ingredient by ID: {}", id);
        return repository.findById(id);
    }

    public ActiveIngredient getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Active ingredient not found with id: " + id));
    }

    public List<ActiveIngredient> getAll() {
        log.debug("Fetching all active ingredients");
        return repository.findAll();
    }

    public ActiveIngredient update(UUID id, ActiveIngredient activeIngredientDetails) {
        ActiveIngredient activeIngredient = getByIdOrThrow(id);
        
        if (activeIngredientDetails.getIngredientName() != null) {
            activeIngredient.setIngredientName(activeIngredientDetails.getIngredientName());
        }
        if (activeIngredientDetails.getDescription() != null) {
            activeIngredient.setDescription(activeIngredientDetails.getDescription());
        }

        validateActiveIngredient(activeIngredient);
        log.info("Updating active ingredient: {}", id);
        return repository.save(activeIngredient);
    }

    public void delete(UUID id) {
        ActiveIngredient activeIngredient = getByIdOrThrow(id);
        log.info("Deleting active ingredient: {}", id);
        repository.delete(activeIngredient);
    }

    private void validateActiveIngredient(ActiveIngredient activeIngredient) {
        if (activeIngredient.getIngredientName() == null || activeIngredient.getIngredientName().trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient name cannot be null or empty");
        }
    }
}

