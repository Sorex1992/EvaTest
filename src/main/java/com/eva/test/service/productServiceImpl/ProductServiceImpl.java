package com.eva.test.service.productServiceImpl;

import com.eva.test.dto.ProductDTO;
import com.eva.test.entity.Product;
import com.eva.test.exception.NotFoundException;
import com.eva.test.mapper.ProductMapper;
import com.eva.test.repository.ProductRepository;
import com.eva.test.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        return productMapper.toDtoList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));;
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String name, Double minPrice, Double maxPrice) {
        log.info("Searching products with name: {}, minPrice: {}, maxPrice: {}", name, minPrice, maxPrice);
        List<Product> products = productRepository.searchProducts(name, minPrice, maxPrice);
        return productMapper.toDtoList(products);
    }

    @Override
    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO) {
        log.info("Saving product: {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        log.info("Updating product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}