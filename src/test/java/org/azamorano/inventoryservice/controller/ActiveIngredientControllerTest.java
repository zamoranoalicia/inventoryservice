package org.azamorano.inventoryservice.controller;

import org.azamorano.inventoryservice.dto.response.ActiveIngredientResponseDto;
import org.azamorano.inventoryservice.entity.ActiveIngredient;
import org.azamorano.inventoryservice.mapper.ActiveIngredientMapper;
import org.azamorano.inventoryservice.service.ActiveIngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("ActiveIngredientController Unit Tests")
class ActiveIngredientControllerTest {

    private ActiveIngredientController controller;

    @Mock
    private ActiveIngredientService service;

    @Mock
    private ActiveIngredientMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ActiveIngredientController(service, mapper);
    }

    @Test
    @DisplayName("Should create ingredient and return response DTO")
    void testCreateIngredient() {
        UUID id = UUID.randomUUID();
        ActiveIngredient entity = new ActiveIngredient();
        entity.setId(id);
        entity.setIngredientName("Aspirin");

        ActiveIngredientResponseDto responseDto = new ActiveIngredientResponseDto(id, "Aspirin", "Pain reliever");

        when(mapper.toEntity(any())).thenReturn(entity);
        when(service.create(any())).thenReturn(entity);
        when(mapper.toResponseDto(any())).thenReturn(responseDto);

        var result = controller.create(any());

        assertNotNull(result);
        assertEquals(201, result.getStatusCode().value());
        verify(service, times(1)).create(any());
    }

    @Test
    @DisplayName("Should get all ingredients")
    void testGetAllIngredients() {
        ActiveIngredient ingredient1 = new ActiveIngredient();
        ingredient1.setId(UUID.randomUUID());
        ingredient1.setIngredientName("Aspirin");

        when(service.getAll()).thenReturn(Arrays.asList(ingredient1));
        when(mapper.toResponseDtoList(any())).thenReturn(Arrays.asList(
            new ActiveIngredientResponseDto(ingredient1.getId(), "Aspirin", null)
        ));

        var result = controller.getAll();

        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        verify(service, times(1)).getAll();
    }

    @Test
    @DisplayName("Should get ingredient by ID")
    void testGetIngredientById() {
        UUID id = UUID.randomUUID();
        ActiveIngredient ingredient = new ActiveIngredient();
        ingredient.setId(id);
        ingredient.setIngredientName("Ibuprofen");

        when(service.getById(id)).thenReturn(Optional.of(ingredient));
        when(mapper.toResponseDto(any())).thenReturn(
            new ActiveIngredientResponseDto(id, "Ibuprofen", null)
        );

        var result = controller.getById(id);

        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        verify(service, times(1)).getById(id);
    }

    @Test
    @DisplayName("Should return 404 when ingredient not found")
    void testGetIngredientNotFound() {
        UUID id = UUID.randomUUID();
        when(service.getById(id)).thenReturn(Optional.empty());

        var result = controller.getById(id);

        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    @DisplayName("Should delete ingredient")
    void testDeleteIngredient() {
        UUID id = UUID.randomUUID();

        var result = controller.delete(id);

        assertEquals(204, result.getStatusCode().value());
        verify(service, times(1)).delete(id);
    }
}


