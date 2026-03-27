package org.azamorano.inventoryservice.service;

import org.azamorano.inventoryservice.entity.Product;
import org.azamorano.inventoryservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandService brandService;

    @Mock
    private LaboratoryService laboratoryService;

    private ProductService productService;

    private Product testProduct;
    private UUID testId;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, brandService, laboratoryService);
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
    void testCreateProduct_Success() {
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product createdProduct = productService.create(testProduct);

        assertNotNull(createdProduct);
        assertEquals("SKU-001", createdProduct.getSku());
        assertEquals("Test Product", createdProduct.getProductName());
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void testCreateProduct_InvalidSku() {
        testProduct.setSku(null);

        assertThrows(IllegalArgumentException.class, () -> productService.create(testProduct));
        verify(productRepository, never()).save(any());
    }

    @Test
    void testCreateProduct_InvalidProductName() {
        testProduct.setProductName("");

        assertThrows(IllegalArgumentException.class, () -> productService.create(testProduct));
        verify(productRepository, never()).save(any());
    }

    @Test
    void testCreateProduct_NegativeReorderLevel() {
        testProduct.setReorderLevel(-5);

        assertThrows(IllegalArgumentException.class, () -> productService.create(testProduct));
        verify(productRepository, never()).save(any());
    }

    @Test
    void testGetById_Success() {
        when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));

        Optional<Product> foundProduct = productService.getById(testId);

        assertTrue(foundProduct.isPresent());
        assertEquals("SKU-001", foundProduct.get().getSku());
        verify(productRepository, times(1)).findById(testId);
    }

    @Test
    void testGetById_NotFound() {
        when(productRepository.findById(testId)).thenReturn(Optional.empty());

        Optional<Product> foundProduct = productService.getById(testId);

        assertFalse(foundProduct.isPresent());
        verify(productRepository, times(1)).findById(testId);
    }

    @Test
    void testGetByIdOrThrow_Success() {
        when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));

        Product foundProduct = productService.getByIdOrThrow(testId);

        assertNotNull(foundProduct);
        assertEquals("SKU-001", foundProduct.getSku());
    }

    @Test
    void testGetByIdOrThrow_NotFound() {
        when(productRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productService.getByIdOrThrow(testId));
    }

    @Test
    void testGetAll_Success() {
        List<Product> productList = new ArrayList<>();
        productList.add(testProduct);
        
        Product secondProduct = new Product();
        secondProduct.setId(UUID.randomUUID());
        secondProduct.setSku("SKU-002");
        secondProduct.setProductName("Second Product");
        secondProduct.setReorderLevel(5);
        productList.add(secondProduct);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> allProducts = productService.getAll();

        assertNotNull(allProducts);
        assertEquals(2, allProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetAll_Empty() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        List<Product> allProducts = productService.getAll();

        assertNotNull(allProducts);
        assertTrue(allProducts.isEmpty());
    }

    @Test
    void testUpdate_Success() {
        when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product updateData = new Product();
        updateData.setProductName("Updated Product");
        updateData.setProductDescription("Updated Description");

        Product updatedProduct = productService.update(testId, updateData);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getProductName());
        assertEquals("Updated Description", updatedProduct.getProductDescription());
        verify(productRepository, times(1)).findById(testId);
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void testUpdate_NotFound() {
        when(productRepository.findById(testId)).thenReturn(Optional.empty());

        Product updateData = new Product();
        updateData.setProductName("Updated Product");

        assertThrows(IllegalArgumentException.class, () -> productService.update(testId, updateData));
        verify(productRepository, never()).save(any());
    }

    @Test
    void testDelete_Success() {
        when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));
        doNothing().when(productRepository).delete(testProduct);

        productService.delete(testId);

        verify(productRepository, times(1)).findById(testId);
        verify(productRepository, times(1)).delete(testProduct);
    }

    @Test
    void testDelete_NotFound() {
        when(productRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productService.delete(testId));
        verify(productRepository, never()).delete(any());
    }

    @Test
    void testFindBySku_Success() {
        when(productRepository.findBySku("SKU-001")).thenReturn(Optional.of(testProduct));

        Optional<Product> foundProduct = productService.findBySku("SKU-001");

        assertTrue(foundProduct.isPresent());
        assertEquals("SKU-001", foundProduct.get().getSku());
        verify(productRepository, times(1)).findBySku("SKU-001");
    }

    @Test
    void testFindBySku_NotFound() {
        when(productRepository.findBySku("SKU-999")).thenReturn(Optional.empty());

        Optional<Product> foundProduct = productService.findBySku("SKU-999");

        assertFalse(foundProduct.isPresent());
    }

    @Test
    void testFindBySanitaryRegistration_Success() {
        when(productRepository.findBySanitaryRegistration("SR-001")).thenReturn(Optional.of(testProduct));

        Optional<Product> foundProduct = productService.findBySanitaryRegistration("SR-001");

        assertTrue(foundProduct.isPresent());
        assertEquals("SR-001", foundProduct.get().getSanitaryRegistration());
    }

    @Test
    void testFindBySanitaryRegistration_NotFound() {
        when(productRepository.findBySanitaryRegistration("SR-999")).thenReturn(Optional.empty());

        Optional<Product> foundProduct = productService.findBySanitaryRegistration("SR-999");

        assertFalse(foundProduct.isPresent());
    }
}

