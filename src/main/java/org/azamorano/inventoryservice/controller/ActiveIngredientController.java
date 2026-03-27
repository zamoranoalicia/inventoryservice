package org.azamorano.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.azamorano.inventoryservice.dto.request.ActiveIngredientRequestDto;
import org.azamorano.inventoryservice.dto.response.ActiveIngredientResponseDto;
import org.azamorano.inventoryservice.mapper.ActiveIngredientMapper;
import org.azamorano.inventoryservice.service.ActiveIngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/active-ingredients")
@Tag(name = "Active Ingredients", description = "APIs for managing active ingredients")
public class ActiveIngredientController {

    private final ActiveIngredientService service;
    private final ActiveIngredientMapper mapper;

    public ActiveIngredientController(ActiveIngredientService service, ActiveIngredientMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new active ingredient", description = "Creates a new active ingredient in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Active ingredient created successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActiveIngredientResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<ActiveIngredientResponseDto> create(@Valid @RequestBody ActiveIngredientRequestDto requestDto) {
        var entity = mapper.toEntity(requestDto);
        var created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(created));
    }

    @GetMapping
    @Operation(summary = "Get all active ingredients", description = "Retrieves a list of all active ingredients")
    @ApiResponse(responseCode = "200", description = "List of active ingredients retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActiveIngredientResponseDto.class)))
    public ResponseEntity<List<ActiveIngredientResponseDto>> getAll() {
        var ingredients = service.getAll();
        return ResponseEntity.ok(mapper.toResponseDtoList(ingredients));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get active ingredient by ID", description = "Retrieves a specific active ingredient by its UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Active ingredient found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActiveIngredientResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Active ingredient not found", content = @Content)
    })
    public ResponseEntity<ActiveIngredientResponseDto> getById(@PathVariable UUID id) {
        var ingredient = service.getById(id);
        return ingredient.map(e -> ResponseEntity.ok(mapper.toResponseDto(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an active ingredient", description = "Updates an existing active ingredient by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Active ingredient updated successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActiveIngredientResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Active ingredient not found", content = @Content)
    })
    public ResponseEntity<ActiveIngredientResponseDto> update(@PathVariable UUID id, @Valid @RequestBody ActiveIngredientRequestDto requestDto) {
        try {
            var entity = mapper.toEntity(requestDto);
            var updated = service.update(id, entity);
            return ResponseEntity.ok(mapper.toResponseDto(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an active ingredient", description = "Deletes an active ingredient by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Active ingredient deleted successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Active ingredient not found", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


