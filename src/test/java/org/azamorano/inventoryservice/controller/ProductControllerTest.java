package org.azamorano.inventoryservice.controller;

import org.azamorano.inventoryservice.dto.response.ProductResponseDto;
import org.azamorano.inventoryservice.entity.Product;
import org.azamorano.inventoryservice.mapper.ProductMapper;
import org.azamorano.inventoryservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("ProductController Unit Tests")
class ProductControllerTest {

    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    private Product testProduct;
    private ProductResponseDto testProductDto;
    private UUID testId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productService, productMapper);
        
        testId = UUID.randomUUID();
        testProduct = new Product();
        testProduct.setId(testId);
        testProduct.setSku("SKU-001");
        testProduct.setBarCode("1234567890");
        testProduct.setProductName("Test Product");
        testProduct.setProductDescription("Test Description");
        testProduct.setPrescriptionRequired(false);
        testProduct.setControlledSubstance(false);
        testProduct.setSanitaryRegistration("SR-001");
        testProduct.setReorderLevel(10);

        testProductDto = new ProductResponseDto();
        testProductDto.setId(testId);
        testProductDto.setSku("SKU-001");
        testProductDto.setProductName("Test Product");
    }

    @Test
    @DisplayName("Should create product successfully")
    void testCreateProduct_Success() {
        when(productMapper.toEntity(any())).thenReturn(testProduct);
        when(productService.create(any())).thenReturn(testProduct);
        when(productMapper.toResponseDto(any())).thenReturn(testProductDto);

        var result = productController.createProduct(any());

        assertNotNull(result);
        assertEquals(201, result.getStatusCode().value());
        verify(productService, times(1)).create(any());
    }

    @Test
    @DisplayName("Should get all products")
    void testGetAllProducts_Success() {
        List<Product> productList = new ArrayList<>();
        productList.add(testProduct);
        
        when(productService.getAll()).thenReturn(productList);
        when(productMapper.toResponseDtoList(any())).thenReturn(List.of(testProductDto));

        var result = productController.getAllProducts();

        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        verify(productService, times(1)).getAll();
    }

    @Test
    @DisplayName("Should get all products when empty")
    void testGetAllProducts_Empty() {
        when(productService.getAll()).thenReturn(new ArrayList<>());
        when(productMapper.toResponseDtoList(any())).thenReturn(new ArrayList<>());

        var result = productController.getAllProducts();

        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    @DisplayName("Should get product by ID successfully")
    void testGetProductById_Success() {
        when(productService.getById(testId)).thenReturn(Optional.of(testProduct));
        when(productMapper.toResponseDto(any())).thenReturn(testProductDto);

        var result = productController.getProductById(testId);

        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        verify(productService, times(1)).getById(testId);
    }

    @Test
    @DisplayName("Should return 404 when product not found")
    void testGetProductById_NotFound() {
        when(productService.getById(testId)).thenReturn(Optional.empty());

        var result = productController.getProductById(testId);

        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    @DisplayName("Should get product by SKU successfully")
    void testGetProductBySku_Success() {
        when(productService.findBySku("SKU-001")).thenReturn(Optional.of(testProduct));
        when(productMapper.toResponseDto(any())).thenReturn(testProductDto);

        var result = productController.getProductBySku("SKU-001");

        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        verify(productService, times(1)).findBySku("SKU-001");
    }

    @Test
    @DisplayName("Should get product by sanitary registration successfully")
    void testGetProductBySanitaryRegistration_Success() {
        when(productService.findBySanitaryRegistration("SR-001")).thenReturn(Optional.of(testProduct));
        when(productMapper.toResponseDto(any())).thenReturn(testProductDto);

        var result = productController.getProductBySanitaryRegistration("SR-001");

        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        verify(productService, times(1)).findBySanitaryRegistration("SR-001");
    }

    @Test
    @DisplayName("Should update product successfully")
    void testUpdateProduct_Success() {
        when(productMapper.toEntity(any())).thenReturn(testProduct);
        when(productService.update(eq(testId), any())).thenReturn(testProduct);
        when(productMapper.toResponseDto(any())).thenReturn(testProductDto);

        var result = productController.updateProduct(testId, any());

        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        verify(productService, times(1)).update(eq(testId), any());
    }

    @Test
    @DisplayName("Should return 404 when updating non-existent product")
    void testUpdateProduct_NotFound() {
        when(productMapper.toEntity(any())).thenReturn(testProduct);
        when(productService.update(eq(testId), any()))
            .thenThrow(new IllegalArgumentException("Product not found"));

        var result = productController.updateProduct(testId, any());

        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    @DisplayName("Should delete product successfully")
    void testDeleteProduct_Success() {
        var result = productController.deleteProduct(testId);

        assertEquals(204, result.getStatusCode().value());
        verify(productService, times(1)).delete(testId);
    }

    @Test
    @DisplayName("Should return 404 when deleting non-existent product")
    void testDeleteProduct_NotFound() {
        doThrow(new IllegalArgumentException("Product not found"))
            .when(productService).delete(testId);

        var result = productController.deleteProduct(testId);

        assertEquals(404, result.getStatusCode().value());
    }
}

