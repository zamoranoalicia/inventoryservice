package org.azamorano.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.azamorano.inventoryservice.dto.request.ProductPresentationRequestDto;
import org.azamorano.inventoryservice.dto.response.ProductPresentationResponseDto;
import org.azamorano.inventoryservice.mapper.ProductPresentationMapper;
import org.azamorano.inventoryservice.service.ProductPresentationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-presentations")
@Tag(name = "Product Presentations", description = "APIs for managing product presentations")
public class ProductPresentationController {

    private final ProductPresentationService service;
    private final ProductPresentationMapper mapper;

    public ProductPresentationController(ProductPresentationService service, ProductPresentationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new product presentation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product presentation created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<ProductPresentationResponseDto> create(@Valid @RequestBody ProductPresentationRequestDto requestDto) {
        var entity = mapper.toEntity(requestDto);
        var product = new org.azamorano.inventoryservice.entity.Product();
        product.setId(requestDto.getProductId());
        entity.setProduct(product);
        
        if (requestDto.getPresentationTypeId() != null) {
            var type = new org.azamorano.inventoryservice.entity.PresentationType();
            type.setId(requestDto.getPresentationTypeId());
            entity.setPresentationType(type);
        }
        
        var created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(created));
    }

    @GetMapping
    @Operation(summary = "Get all product presentations")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<ProductPresentationResponseDto>> getAll() {
        var presentations = service.getAll();
        return ResponseEntity.ok(mapper.toResponseDtoList(presentations));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product presentation by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product presentation found"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<ProductPresentationResponseDto> getById(@PathVariable UUID id) {
        var presentation = service.getById(id);
        return presentation.map(e -> ResponseEntity.ok(mapper.toResponseDto(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product presentation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated successfully"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<ProductPresentationResponseDto> update(@PathVariable UUID id, @Valid @RequestBody ProductPresentationRequestDto requestDto) {
        try {
            var entity = mapper.toEntity(requestDto);
            var product = new org.azamorano.inventoryservice.entity.Product();
            product.setId(requestDto.getProductId());
            entity.setProduct(product);
            
            if (requestDto.getPresentationTypeId() != null) {
                var type = new org.azamorano.inventoryservice.entity.PresentationType();
                type.setId(requestDto.getPresentationTypeId());
                entity.setPresentationType(type);
            }
            
            var updated = service.update(id, entity);
            return ResponseEntity.ok(mapper.toResponseDto(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product presentation")
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

