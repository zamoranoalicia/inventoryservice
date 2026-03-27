package org.azamorano.inventoryservice.service;

import org.azamorano.inventoryservice.entity.Price;
import org.azamorano.inventoryservice.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PriceService Unit Tests")
class PriceServiceTest {

    private PriceService service;

    @Mock
    private PriceRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new PriceService(repository);
    }

    @Test
    @DisplayName("Should create price successfully")
    void testCreateSuccess() {
        Price price = new Price();
        price.setAmount(BigDecimal.valueOf(10.50));
        price.setCurrency("USD");

        when(repository.save(price)).thenReturn(price);

        Price result = service.create(price);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(10.50), result.getAmount());
        assertEquals("USD", result.getCurrency());
        verify(repository, times(1)).save(price);
    }

    @Test
    @DisplayName("Should throw exception when amount is zero or negative")
    void testCreateWithInvalidAmount() {
        Price price = new Price();
        price.setAmount(BigDecimal.ZERO);
        price.setCurrency("USD");

        assertThrows(IllegalArgumentException.class, () -> service.create(price));
    }

    @Test
    @DisplayName("Should throw exception when amount is null")
    void testCreateWithNullAmount() {
        Price price = new Price();
        price.setCurrency("USD");

        assertThrows(IllegalArgumentException.class, () -> service.create(price));
    }

    @Test
    @DisplayName("Should retrieve price by ID")
    void testGetByIdSuccess() {
        UUID id = UUID.randomUUID();
        Price price = new Price();
        price.setId(id);
        price.setAmount(BigDecimal.valueOf(25.99));

        when(repository.findById(id)).thenReturn(Optional.of(price));

        Optional<Price> result = service.getById(id);

        assertTrue(result.isPresent());
        assertEquals(BigDecimal.valueOf(25.99), result.get().getAmount());
    }

    @Test
    @DisplayName("Should update price successfully")
    void testUpdateSuccess() {
        UUID id = UUID.randomUUID();
        Price existing = new Price();
        existing.setId(id);
        existing.setAmount(BigDecimal.valueOf(10.00));

        Price updated = new Price();
        updated.setAmount(BigDecimal.valueOf(15.00));

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);

        Price result = service.update(id, updated);

        assertEquals(BigDecimal.valueOf(15.00), result.getAmount());
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should delete price successfully")
    void testDeleteSuccess() {
        UUID id = UUID.randomUUID();
        Price price = new Price();
        price.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(price));

        service.delete(id);

        verify(repository, times(1)).delete(price);
    }
}

