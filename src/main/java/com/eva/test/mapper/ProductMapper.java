package com.eva.test.mapper;

import com.eva.test.dto.ProductDTO;
import com.eva.test.entity.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ModelMapper modelMapper;

    public ProductDTO toDto(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product toEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public List<ProductDTO> toDtoList(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Product> toEntityList(List<ProductDTO> productDTOs) {
        return productDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}