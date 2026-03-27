package org.azamorano.inventoryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.azamorano.inventoryservice.entity.Price;
import org.azamorano.inventoryservice.repository.PriceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class PriceService {

    private final PriceRepository repository;

    public PriceService(PriceRepository repository) {
        this.repository = repository;
    }

    public Price create(Price price) {
        validatePrice(price);
        log.info("Creating price: {} {}", price.getAmount(), price.getCurrency());
        return repository.save(price);
    }

    public Optional<Price> getById(UUID id) {
        log.debug("Fetching price by ID: {}", id);
        return repository.findById(id);
    }

    public Price getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Price not found with id: " + id));
    }

    public List<Price> getAll() {
        log.debug("Fetching all prices");
        return repository.findAll();
    }

    public Price update(UUID id, Price priceDetails) {
        Price price = getByIdOrThrow(id);
        
        if (priceDetails.getAmount() != null) {
            price.setAmount(priceDetails.getAmount());
        }
        if (priceDetails.getCurrency() != null) {
            price.setCurrency(priceDetails.getCurrency());
        }

        validatePrice(price);
        log.info("Updating price: {}", id);
        return repository.save(price);
    }

    public void delete(UUID id) {
        Price price = getByIdOrThrow(id);
        log.info("Deleting price: {}", id);
        repository.delete(price);
    }

    private void validatePrice(Price price) {
        if (price.getAmount() == null || price.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price amount must be greater than zero");
        }
    }
}

