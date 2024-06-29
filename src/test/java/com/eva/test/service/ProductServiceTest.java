package com.eva.test.service;

import com.eva.test.dto.ProductDTO;
import com.eva.test.entity.Product;
import com.eva.test.exception.NotFoundException;
import com.eva.test.mapper.ProductMapper;
import com.eva.test.repository.ProductRepository;
import com.eva.test.service.productServiceImpl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setCountry("Test Country");
        product.setPrice(100.0);
        product.setQuantity(10);

        productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setCountry("Test Country");
        productDTO.setPrice(100.0);
        productDTO.setQuantity(10);
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(productMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(productDTO));

        List<ProductDTO> productList = productService.getAllProducts();

        assertNotNull(productList);
        assertEquals(1, productList.size());
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).toDtoList(anyList());
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(any(Product.class))).thenReturn(productDTO);

        ProductDTO foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());
        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testSearchProductsWithPriceRange() {
        when(productRepository.searchProducts(anyString(), anyDouble(), anyDouble()))
                .thenReturn(Collections.singletonList(product));
        when(productMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(productDTO));

        List<ProductDTO> productList = productService.searchProducts("Test", 50.0, 150.0);

        assertNotNull(productList);
        assertEquals(1, productList.size());
        verify(productRepository, times(1)).searchProducts(anyString(), anyDouble(), anyDouble());
        verify(productMapper, times(1)).toDtoList(anyList());
    }

    @Test
    void testSearchProductsWithoutPriceRange() {
        when(productRepository.searchProducts(anyString(), isNull(), isNull()))
                .thenReturn(Collections.singletonList(product));
        when(productMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(productDTO));

        List<ProductDTO> productList = productService.searchProducts("Test", null, null);

        assertNotNull(productList);
        assertEquals(1, productList.size());
        verify(productRepository, times(1)).searchProducts(anyString(), isNull(), isNull());
        verify(productMapper, times(1)).toDtoList(anyList());
    }


    @Test
    void testSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDTO);

        ProductDTO savedProduct = productService.saveProduct(productDTO);

        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toEntity(any(ProductDTO.class));
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDTO);

        ProductDTO updatedProduct = productService.updateProduct(1L, productDTO);

        assertNotNull(updatedProduct);
        assertEquals("Test Product", updatedProduct.getName());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    void testUpdateProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.updateProduct(1L, productDTO));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProductNotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).existsById(1L);
    }
}

