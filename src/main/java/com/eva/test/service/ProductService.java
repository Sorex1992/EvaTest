package com.eva.test.service;

import com.eva.test.dto.ProductDTO;
import com.eva.test.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    List<ProductDTO> searchProducts(String name, Double minPrice, Double maxPrice);
    ProductDTO saveProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}