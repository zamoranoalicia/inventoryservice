package org.azamorano.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.azamorano.inventoryservice.dto.request.ProductSupplierRequestDto;
import org.azamorano.inventoryservice.dto.response.ProductSupplierResponseDto;
import org.azamorano.inventoryservice.mapper.ProductSupplierMapper;
import org.azamorano.inventoryservice.service.ProductSupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-suppliers")
@Tag(name = "Product Suppliers", description = "APIs for managing product suppliers")
public class ProductSupplierController {

    private final ProductSupplierService service;
    private final ProductSupplierMapper mapper;

    public ProductSupplierController(ProductSupplierService service, ProductSupplierMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new product supplier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product supplier created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<ProductSupplierResponseDto> create(@Valid @RequestBody ProductSupplierRequestDto requestDto) {
        var entity = mapper.toEntity(requestDto);
        var created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(created));
    }

    @GetMapping
    @Operation(summary = "Get all product suppliers")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<ProductSupplierResponseDto>> getAll() {
        var suppliers = service.getAll();
        return ResponseEntity.ok(mapper.toResponseDtoList(suppliers));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product supplier by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product supplier found"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<ProductSupplierResponseDto> getById(@PathVariable UUID id) {
        var supplier = service.getById(id);
        return supplier.map(e -> ResponseEntity.ok(mapper.toResponseDto(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product supplier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated successfully"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<ProductSupplierResponseDto> update(@PathVariable UUID id, @Valid @RequestBody ProductSupplierRequestDto requestDto) {
        try {
            var entity = mapper.toEntity(requestDto);
            var updated = service.update(id, entity);
            return ResponseEntity.ok(mapper.toResponseDto(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product supplier")
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

