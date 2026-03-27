package org.azamorano.inventoryservice.service;

import org.azamorano.inventoryservice.entity.PresentationType;
import org.azamorano.inventoryservice.repository.PresentationTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PresentationTypeService Unit Tests")
class PresentationTypeServiceTest {

    private PresentationTypeService service;

    @Mock
    private PresentationTypeRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new PresentationTypeService(repository);
    }

    @Test
    @DisplayName("Should create presentation type successfully")
    void testCreateSuccess() {
        PresentationType type = new PresentationType();
        type.setName("Tablet");
        type.setDescription("Oral tablet form");

        when(repository.save(type)).thenReturn(type);

        PresentationType result = service.create(type);

        assertNotNull(result);
        assertEquals("Tablet", result.getName());
        verify(repository, times(1)).save(type);
    }

    @Test
    @DisplayName("Should throw exception when name is blank")
    void testCreateWithBlankName() {
        PresentationType type = new PresentationType();
        type.setName("");

        assertThrows(IllegalArgumentException.class, () -> service.create(type));
    }

    @Test
    @DisplayName("Should retrieve presentation type by ID")
    void testGetByIdSuccess() {
        UUID id = UUID.randomUUID();
        PresentationType type = new PresentationType();
        type.setId(id);
        type.setName("Capsule");

        when(repository.findById(id)).thenReturn(Optional.of(type));

        Optional<PresentationType> result = service.getById(id);

        assertTrue(result.isPresent());
        assertEquals("Capsule", result.get().getName());
    }

    @Test
    @DisplayName("Should update presentation type successfully")
    void testUpdateSuccess() {
        UUID id = UUID.randomUUID();
        PresentationType existing = new PresentationType();
        existing.setId(id);
        existing.setName("Pill");

        PresentationType updated = new PresentationType();
        updated.setName("Injection");

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);

        PresentationType result = service.update(id, updated);

        assertEquals("Injection", result.getName());
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should delete presentation type successfully")
    void testDeleteSuccess() {
        UUID id = UUID.randomUUID();
        PresentationType type = new PresentationType();
        type.setId(id);
        type.setName("Syrup");

        when(repository.findById(id)).thenReturn(Optional.of(type));

        service.delete(id);

        verify(repository, times(1)).delete(type);
    }
}

