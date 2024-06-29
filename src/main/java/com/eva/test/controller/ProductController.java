package com.eva.test.controller;

import com.eva.test.dto.ProductDTO;
import com.eva.test.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Fetch all products")
    public List<ProductDTO> getAllProducts() {
        log.info("Received request to get all products");
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Fetch a product by its ID")
    public ProductDTO getProductById(@PathVariable Long id) {
        log.info("Received request to get product with id: {}", id);
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search for products by name and optionally by price range")
    public List<ProductDTO> searchProducts(@RequestParam String name,
                                           @RequestParam(required = false) Double minPrice,
                                           @RequestParam(required = false) Double maxPrice) {
        log.info("Received request to search products with name: {}, minPrice: {}, maxPrice: {}",
                name, minPrice, maxPrice);
        return productService.searchProducts(name, minPrice, maxPrice);
    }

    @PostMapping
    @Operation(summary = "Add a new product", description = "Add a new product")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        log.info("Received request to add product: {}", productDTO);
        return productService.saveProduct(productDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Update an existing product")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        log.info("Received request to update product with id: {}", id);
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Delete a product")
    public void deleteProduct(@PathVariable Long id) {
        log.info("Received request to delete product with id: {}", id);
        productService.deleteProduct(id);
    }
}