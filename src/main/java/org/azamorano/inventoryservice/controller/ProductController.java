package org.azamorano.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.azamorano.inventoryservice.dto.request.ProductRequestDto;
import org.azamorano.inventoryservice.dto.response.ProductResponseDto;
import org.azamorano.inventoryservice.mapper.ProductMapper;
import org.azamorano.inventoryservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for managing products in the inventory")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper mapper;

    public ProductController(ProductService productService, ProductMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product in the inventory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        var product = mapper.toEntity(requestDto);
        var createdProduct = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(createdProduct));
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a list of all products in the inventory")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class)))
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        var products = productService.getAll();
        return ResponseEntity.ok(mapper.toResponseDtoList(products));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a specific product by its UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        var product = productService.getById(id);
        return product.map(p -> ResponseEntity.ok(mapper.toResponseDto(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Updates an existing product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductRequestDto requestDto) {
        try {
            var product = mapper.toEntity(requestDto);
            var updatedProduct = productService.update(id, product);
            return ResponseEntity.ok(mapper.toResponseDto(updatedProduct));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        try {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Get product by SKU", description = "Retrieves a product by its SKU")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<ProductResponseDto> getProductBySku(@PathVariable String sku) {
        var product = productService.findBySku(sku);
        return product.map(p -> ResponseEntity.ok(mapper.toResponseDto(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/sanitary/{sanitaryRegistration}")
    @Operation(summary = "Get product by sanitary registration", description = "Retrieves a product by its sanitary registration number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<ProductResponseDto> getProductBySanitaryRegistration(@PathVariable String sanitaryRegistration) {
        var product = productService.findBySanitaryRegistration(sanitaryRegistration);
        return product.map(p -> ResponseEntity.ok(mapper.toResponseDto(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
