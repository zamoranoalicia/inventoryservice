package org.azamorano.inventoryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.azamorano.inventoryservice.entity.DateAlert;
import org.azamorano.inventoryservice.repository.DateAlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class DateAlertService {

    private final DateAlertRepository repository;

    public DateAlertService(DateAlertRepository repository) {
        this.repository = repository;
    }

    public DateAlert create(DateAlert dateAlert) {
        validateDateAlert(dateAlert);
        log.info("Creating date alert with expiration date: {}", dateAlert.getExpirationDate());
        return repository.save(dateAlert);
    }

    public Optional<DateAlert> getById(UUID id) {
        log.debug("Fetching date alert by ID: {}", id);
        return repository.findById(id);
    }

    public DateAlert getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Date alert not found with id: " + id));
    }

    public List<DateAlert> getAll() {
        log.debug("Fetching all date alerts");
        return repository.findAll();
    }

    public DateAlert update(UUID id, DateAlert dateAlertDetails) {
        DateAlert dateAlert = getByIdOrThrow(id);
        
        if (dateAlertDetails.getExpirationDate() != null) {
            dateAlert.setExpirationDate(dateAlertDetails.getExpirationDate());
        }
        if (dateAlertDetails.getAlertDate() != null) {
            dateAlert.setAlertDate(dateAlertDetails.getAlertDate());
        }

        validateDateAlert(dateAlert);
        log.info("Updating date alert: {}", id);
        return repository.save(dateAlert);
    }

    public void delete(UUID id) {
        DateAlert dateAlert = getByIdOrThrow(id);
        log.info("Deleting date alert: {}", id);
        repository.delete(dateAlert);
    }

    private void validateDateAlert(DateAlert dateAlert) {
        if (dateAlert.getExpirationDate() == null) {
            throw new IllegalArgumentException("Expiration date cannot be null");
        }
    }
}

