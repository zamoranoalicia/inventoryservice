package org.azamorano.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.azamorano.inventoryservice.dto.request.PresentationTypeRequestDto;
import org.azamorano.inventoryservice.dto.response.PresentationTypeResponseDto;
import org.azamorano.inventoryservice.mapper.PresentationTypeMapper;
import org.azamorano.inventoryservice.service.PresentationTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/presentation-types")
@Tag(name = "Presentation Types", description = "APIs for managing presentation types")
public class PresentationTypeController {

    private final PresentationTypeService service;
    private final PresentationTypeMapper mapper;

    public PresentationTypeController(PresentationTypeService service, PresentationTypeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new presentation type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Presentation type created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<PresentationTypeResponseDto> create(@Valid @RequestBody PresentationTypeRequestDto requestDto) {
        var entity = mapper.toEntity(requestDto);
        var created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(created));
    }

    @GetMapping
    @Operation(summary = "Get all presentation types")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<PresentationTypeResponseDto>> getAll() {
        var types = service.getAll();
        return ResponseEntity.ok(mapper.toResponseDtoList(types));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get presentation type by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Presentation type found"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<PresentationTypeResponseDto> getById(@PathVariable UUID id) {
        var type = service.getById(id);
        return type.map(e -> ResponseEntity.ok(mapper.toResponseDto(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a presentation type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated successfully"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<PresentationTypeResponseDto> update(@PathVariable UUID id, @Valid @RequestBody PresentationTypeRequestDto requestDto) {
        try {
            var entity = mapper.toEntity(requestDto);
            var updated = service.update(id, entity);
            return ResponseEntity.ok(mapper.toResponseDto(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a presentation type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
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

