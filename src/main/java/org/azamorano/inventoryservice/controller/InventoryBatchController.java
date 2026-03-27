package org.azamorano.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.azamorano.inventoryservice.dto.request.InventoryBatchRequestDto;
import org.azamorano.inventoryservice.dto.response.InventoryBatchResponseDto;
import org.azamorano.inventoryservice.mapper.InventoryBatchMapper;
import org.azamorano.inventoryservice.service.InventoryBatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory-batches")
@Tag(name = "Inventory Batches", description = "APIs for managing inventory batches")
public class InventoryBatchController {

    private final InventoryBatchService service;
    private final InventoryBatchMapper mapper;

    public InventoryBatchController(InventoryBatchService service, InventoryBatchMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new inventory batch")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Inventory batch created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<InventoryBatchResponseDto> create(@Valid @RequestBody InventoryBatchRequestDto requestDto) {
        var entity = mapper.toEntity(requestDto);
        var product = new org.azamorano.inventoryservice.entity.Product();
        product.setId(requestDto.getProductId());
        entity.setProduct(product);
        
        if (requestDto.getSupplierId() != null) {
            var supplier = new org.azamorano.inventoryservice.entity.ProductSupplier();
            supplier.setId(requestDto.getSupplierId());
            entity.setSupplier(supplier);
        }
        
        var created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(created));
    }

    @GetMapping
    @Operation(summary = "Get all inventory batches")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<InventoryBatchResponseDto>> getAll() {
        var batches = service.getAll();
        return ResponseEntity.ok(mapper.toResponseDtoList(batches));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get inventory batch by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventory batch found"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<InventoryBatchResponseDto> getById(@PathVariable UUID id) {
        var batch = service.getById(id);
        return batch.map(e -> ResponseEntity.ok(mapper.toResponseDto(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an inventory batch")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated successfully"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<InventoryBatchResponseDto> update(@PathVariable UUID id, @Valid @RequestBody InventoryBatchRequestDto requestDto) {
        try {
            var entity = mapper.toEntity(requestDto);
            var product = new org.azamorano.inventoryservice.entity.Product();
            product.setId(requestDto.getProductId());
            entity.setProduct(product);
            
            if (requestDto.getSupplierId() != null) {
                var supplier = new org.azamorano.inventoryservice.entity.ProductSupplier();
                supplier.setId(requestDto.getSupplierId());
                entity.setSupplier(supplier);
            }
            
            var updated = service.update(id, entity);
            return ResponseEntity.ok(mapper.toResponseDto(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an inventory batch")
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

