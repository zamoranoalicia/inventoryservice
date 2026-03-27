package org.azamorano.inventoryservice.service;

import org.azamorano.inventoryservice.entity.ActiveIngredient;
import org.azamorano.inventoryservice.repository.ActiveIngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ActiveIngredientService Unit Tests")
class ActiveIngredientServiceTest {

    private ActiveIngredientService service;

    @Mock
    private ActiveIngredientRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ActiveIngredientService(repository);
    }

    @Test
    @DisplayName("Should create active ingredient successfully")
    void testCreateSuccess() {
        ActiveIngredient ingredient = new ActiveIngredient();
        ingredient.setIngredientName("Aspirin");
        ingredient.setDescription("Pain reliever");

        when(repository.save(ingredient)).thenReturn(ingredient);

        ActiveIngredient result = service.create(ingredient);

        assertNotNull(result);
        assertEquals("Aspirin", result.getIngredientName());
        verify(repository, times(1)).save(ingredient);
    }

    @Test
    @DisplayName("Should throw exception when ingredient name is null")
    void testCreateWithNullName() {
        ActiveIngredient ingredient = new ActiveIngredient();
        ingredient.setDescription("Pain reliever");

        assertThrows(IllegalArgumentException.class, () -> service.create(ingredient));
    }

    @Test
    @DisplayName("Should retrieve ingredient by ID")
    void testGetByIdSuccess() {
        UUID id = UUID.randomUUID();
        ActiveIngredient ingredient = new ActiveIngredient();
        ingredient.setId(id);
        ingredient.setIngredientName("Ibuprofen");

        when(repository.findById(id)).thenReturn(Optional.of(ingredient));

        Optional<ActiveIngredient> result = service.getById(id);

        assertTrue(result.isPresent());
        assertEquals("Ibuprofen", result.get().getIngredientName());
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return empty when ingredient not found")
    void testGetByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<ActiveIngredient> result = service.getById(id);

        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should throw exception when getting by ID that doesn't exist")
    void testGetByIdOrThrowNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.getByIdOrThrow(id));
    }

    @Test
    @DisplayName("Should update ingredient successfully")
    void testUpdateSuccess() {
        UUID id = UUID.randomUUID();
        ActiveIngredient existing = new ActiveIngredient();
        existing.setId(id);
        existing.setIngredientName("Old Name");

        ActiveIngredient updated = new ActiveIngredient();
        updated.setIngredientName("New Name");

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);

        ActiveIngredient result = service.update(id, updated);

        assertEquals("New Name", result.getIngredientName());
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should delete ingredient successfully")
    void testDeleteSuccess() {
        UUID id = UUID.randomUUID();
        ActiveIngredient ingredient = new ActiveIngredient();
        ingredient.setId(id);
        ingredient.setIngredientName("Aspirin");

        when(repository.findById(id)).thenReturn(Optional.of(ingredient));

        service.delete(id);

        verify(repository, times(1)).delete(ingredient);
    }
}

