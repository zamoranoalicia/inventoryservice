package org.azamorano.inventoryservice.controller;

import org.azamorano.inventoryservice.entity.Product;
import org.azamorano.inventoryservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Product testProduct;
    private UUID testId;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        
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
    }

    @Test
    void testCreateProduct_Success() throws Exception {
        when(productService.create(any(Product.class))).thenReturn(testProduct);

        mockMvc.perform(post("/api/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.sku").value("SKU-001"))
                .andExpect(jsonPath("$.productName").value("Test Product"));

        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    void testGetAllProducts_Success() throws Exception {
        List<Product> productList = new ArrayList<>();
        productList.add(testProduct);
        
        when(productService.getAll()).thenReturn(productList);

        mockMvc.perform(get("/api/products")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value("SKU-001"))
                .andExpect(jsonPath("$[0].productName").value("Test Product"));

        verify(productService, times(1)).getAll();
    }

    @Test
    void testGetAllProducts_Empty() throws Exception {
        when(productService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/products")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetProductById_Success() throws Exception {
        when(productService.getById(testId)).thenReturn(Optional.of(testProduct));

        mockMvc.perform(get("/api/products/" + testId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("SKU-001"))
                .andExpect(jsonPath("$.productName").value("Test Product"));

        verify(productService, times(1)).getById(testId);
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        when(productService.getById(testId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/" + testId)
                .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getById(testId);
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        Product updateData = new Product();
        updateData.setProductName("Updated Product");
        testProduct.setProductName("Updated Product");

        when(productService.update(eq(testId), any(Product.class))).thenReturn(testProduct);

        mockMvc.perform(put("/api/products/" + testId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Updated Product"));

        verify(productService, times(1)).update(eq(testId), any(Product.class));
    }

    @Test
    void testUpdateProduct_NotFound() throws Exception {
        Product updateData = new Product();
        updateData.setProductName("Updated Product");

        when(productService.update(eq(testId), any(Product.class)))
                .thenThrow(new IllegalArgumentException("Product not found"));

        mockMvc.perform(put("/api/products/" + testId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        doNothing().when(productService).delete(testId);

        mockMvc.perform(delete("/api/products/" + testId)
                .contentType("application/json"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).delete(testId);
    }

    @Test
    void testDeleteProduct_NotFound() throws Exception {
        doThrow(new IllegalArgumentException("Product not found"))
                .when(productService).delete(testId);

        mockMvc.perform(delete("/api/products/" + testId)
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetProductBySku_Success() throws Exception {
        when(productService.findBySku("SKU-001")).thenReturn(Optional.of(testProduct));

        mockMvc.perform(get("/api/products/sku/SKU-001")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("SKU-001"))
                .andExpect(jsonPath("$.productName").value("Test Product"));

        verify(productService, times(1)).findBySku("SKU-001");
    }

    @Test
    void testGetProductBySku_NotFound() throws Exception {
        when(productService.findBySku("SKU-999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/sku/SKU-999")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetProductBySanitaryRegistration_Success() throws Exception {
        when(productService.findBySanitaryRegistration("SR-001")).thenReturn(Optional.of(testProduct));

        mockMvc.perform(get("/api/products/sanitary/SR-001")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sanitaryRegistration").value("SR-001"));

        verify(productService, times(1)).findBySanitaryRegistration("SR-001");
    }

    @Test
    void testGetProductBySanitaryRegistration_NotFound() throws Exception {
        when(productService.findBySanitaryRegistration("SR-999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/sanitary/SR-999")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}




