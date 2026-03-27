package org.azamorano.inventoryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.azamorano.inventoryservice.entity.TherapeuticAction;
import org.azamorano.inventoryservice.repository.TherapeuticActionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class TherapeuticActionService {

    private final TherapeuticActionRepository repository;

    public TherapeuticActionService(TherapeuticActionRepository repository) {
        this.repository = repository;
    }

    public TherapeuticAction create(TherapeuticAction therapeuticAction) {
        validateTherapeuticAction(therapeuticAction);
        log.info("Creating therapeutic action: {}", therapeuticAction.getAction());
        return repository.save(therapeuticAction);
    }

    public Optional<TherapeuticAction> getById(UUID id) {
        log.debug("Fetching therapeutic action by ID: {}", id);
        return repository.findById(id);
    }

    public TherapeuticAction getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Therapeutic action not found with id: " + id));
    }

    public List<TherapeuticAction> getAll() {
        log.debug("Fetching all therapeutic actions");
        return repository.findAll();
    }

    public TherapeuticAction update(UUID id, TherapeuticAction therapeuticActionDetails) {
        TherapeuticAction therapeuticAction = getByIdOrThrow(id);
        
        if (therapeuticActionDetails.getAction() != null) {
            therapeuticAction.setAction(therapeuticActionDetails.getAction());
        }
        if (therapeuticActionDetails.getDescription() != null) {
            therapeuticAction.setDescription(therapeuticActionDetails.getDescription());
        }

        validateTherapeuticAction(therapeuticAction);
        log.info("Updating therapeutic action: {}", id);
        return repository.save(therapeuticAction);
    }

    public void delete(UUID id) {
        TherapeuticAction therapeuticAction = getByIdOrThrow(id);
        log.info("Deleting therapeutic action: {}", id);
        repository.delete(therapeuticAction);
    }

    private void validateTherapeuticAction(TherapeuticAction therapeuticAction) {
        if (therapeuticAction.getAction() == null || therapeuticAction.getAction().trim().isEmpty()) {
            throw new IllegalArgumentException("Action cannot be null or empty");
        }
    }
}

