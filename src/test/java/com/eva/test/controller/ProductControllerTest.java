package com.eva.test.controller;

import com.eva.test.dto.ProductDTO;
import com.eva.test.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setCountry("Test Country");
        productDTO.setPrice(100.0);
        productDTO.setQuantity(10);
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<ProductDTO> productList = Collections.singletonList(productDTO);
        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById() throws Exception {
        when(productService.getProductById(anyLong())).thenReturn(productDTO);

        mockMvc.perform(get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).getProductById(anyLong());
    }

    @Test
    void testSearchProductsWithPriceRange() throws Exception {
        List<ProductDTO> productList = Collections.singletonList(productDTO);
        when(productService.searchProducts(anyString(), anyDouble(), anyDouble())).thenReturn(productList);

        mockMvc.perform(get("/products/search")
                        .param("name", "Test")
                        .param("minPrice", "50")
                        .param("maxPrice", "150")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"));

        verify(productService, times(1)).searchProducts(anyString(), anyDouble(), anyDouble());
    }

    @Test
    void testSearchProductsWithoutPriceRange() throws Exception {
        List<ProductDTO> productList = Collections.singletonList(productDTO);
        when(productService.searchProducts(anyString(), isNull(), isNull())).thenReturn(productList);

        mockMvc.perform(get("/products/search")
                        .param("name", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"));

        verify(productService, times(1)).searchProducts(anyString(), isNull(), isNull());
    }

    @Test
    void testAddProduct() throws Exception {
        when(productService.saveProduct(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).saveProduct(any(ProductDTO.class));
    }

    @Test
    void testUpdateProduct() throws Exception {
        when(productService.updateProduct(anyLong(), any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).updateProduct(anyLong(), any(ProductDTO.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyLong());

        mockMvc.perform(delete("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(anyLong());
    }
}
