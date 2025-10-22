package com.himalayanwc.products.mapper;

import com.himalayanwc.products.Dto.response.ProductResponseDTO;
import com.himalayanwc.products.Dto.response.ProductSpecificationResponseDTO;
import com.himalayanwc.products.model.Product;
import com.himalayanwc.products.model.ProductSpecification;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductResponseDTO toResponse(Product product) {
        if (product == null) {
            return null;
        }

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setBrand(product.getBrand());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setImgUrl(product.getImgUrl());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setStockQuantity(product.getStockQuantity());

        // Map category
        if (product.getCategory() != null) {
            responseDTO.setCategory(CategoryMapper.toResponse(product.getCategory()));
        }

        // Map specifications
        if (product.getProductSpecifications() != null) {
            responseDTO.setSpecifications(toSpecificationResponseList(product.getProductSpecifications()));
        }

        return responseDTO;
    }

    private static List<ProductSpecificationResponseDTO> toSpecificationResponseList(List<ProductSpecification> productSpecifications) {
        return productSpecifications.stream()
                .map(ProductMapper::toSpecificationResponse)
                .collect(Collectors.toList());
    }

    private static ProductSpecificationResponseDTO toSpecificationResponse(ProductSpecification productSpec) {
        ProductSpecificationResponseDTO dto = new ProductSpecificationResponseDTO();
        dto.setId(productSpec.getId());
        dto.setValue(productSpec.getValue());

        if (productSpec.getSpecificationField() != null) {
            dto.setFieldName(productSpec.getSpecificationField().getFieldName());
            dto.setFieldType(productSpec.getSpecificationField().getFieldType().toString());
        }

        return dto;
    }

    public static List<ProductResponseDTO> toResponseList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }
}