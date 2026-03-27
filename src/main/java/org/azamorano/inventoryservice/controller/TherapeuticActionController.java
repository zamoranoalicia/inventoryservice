package org.azamorano.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.azamorano.inventoryservice.dto.request.TherapeuticActionRequestDto;
import org.azamorano.inventoryservice.dto.response.TherapeuticActionResponseDto;
import org.azamorano.inventoryservice.mapper.TherapeuticActionMapper;
import org.azamorano.inventoryservice.service.TherapeuticActionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/therapeutic-actions")
@Tag(name = "Therapeutic Actions", description = "APIs for managing therapeutic actions")
public class TherapeuticActionController {

    private final TherapeuticActionService service;
    private final TherapeuticActionMapper mapper;

    public TherapeuticActionController(TherapeuticActionService service, TherapeuticActionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new therapeutic action")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Therapeutic action created successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TherapeuticActionResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<TherapeuticActionResponseDto> create(@Valid @RequestBody TherapeuticActionRequestDto requestDto) {
        var entity = mapper.toEntity(requestDto);
        var created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(created));
    }

    @GetMapping
    @Operation(summary = "Get all therapeutic actions")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<TherapeuticActionResponseDto>> getAll() {
        var actions = service.getAll();
        return ResponseEntity.ok(mapper.toResponseDtoList(actions));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get therapeutic action by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Therapeutic action found"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<TherapeuticActionResponseDto> getById(@PathVariable UUID id) {
        var action = service.getById(id);
        return action.map(e -> ResponseEntity.ok(mapper.toResponseDto(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a therapeutic action")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated successfully"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<TherapeuticActionResponseDto> update(@PathVariable UUID id, @Valid @RequestBody TherapeuticActionRequestDto requestDto) {
        try {
            var entity = mapper.toEntity(requestDto);
            var updated = service.update(id, entity);
            return ResponseEntity.ok(mapper.toResponseDto(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a therapeutic action")
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

