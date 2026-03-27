package org.azamorano.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.azamorano.inventoryservice.dto.request.PriceRequestDto;
import org.azamorano.inventoryservice.dto.response.PriceResponseDto;
import org.azamorano.inventoryservice.mapper.PriceMapper;
import org.azamorano.inventoryservice.service.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prices")
@Tag(name = "Prices", description = "APIs for managing prices")
public class PriceController {

    private final PriceService service;
    private final PriceMapper mapper;

    public PriceController(PriceService service, PriceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new price")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Price created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<PriceResponseDto> create(@Valid @RequestBody PriceRequestDto requestDto) {
        var entity = mapper.toEntity(requestDto);
        var created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(created));
    }

    @GetMapping
    @Operation(summary = "Get all prices")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<PriceResponseDto>> getAll() {
        var prices = service.getAll();
        return ResponseEntity.ok(mapper.toResponseDtoList(prices));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get price by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Price found"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<PriceResponseDto> getById(@PathVariable UUID id) {
        var price = service.getById(id);
        return price.map(e -> ResponseEntity.ok(mapper.toResponseDto(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a price")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated successfully"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<PriceResponseDto> update(@PathVariable UUID id, @Valid @RequestBody PriceRequestDto requestDto) {
        try {
            var entity = mapper.toEntity(requestDto);
            var updated = service.update(id, entity);
            return ResponseEntity.ok(mapper.toResponseDto(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a price")
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

