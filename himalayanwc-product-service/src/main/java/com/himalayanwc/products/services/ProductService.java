package com.himalayanwc.products.services;

import com.himalayanwc.products.Dto.create.ProductCreateDTO;
import com.himalayanwc.products.Dto.response.ProductResponseDTO;
import com.himalayanwc.products.mapper.ProductMapper;
import com.himalayanwc.products.model.*;
import com.himalayanwc.products.repo.CategoryRepo;
import com.himalayanwc.products.repo.ProductRepo;
import com.himalayanwc.products.repo.SpecificationFieldRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final SpecificationFieldRepo specificationFieldRepo;

    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo,
                          SpecificationFieldRepo specificationFieldRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.specificationFieldRepo = specificationFieldRepo;
    }

    public ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO) {
        if (productCreateDTO.getCategoryId() == null) {
            throw new RuntimeException("Category ID is required");
        }

        if (productRepo.existsByNameAndCategoryId(productCreateDTO.getName(), productCreateDTO.getCategoryId())) {
            throw new RuntimeException("Product with name '" + productCreateDTO.getName() + "' already exists in this category");
        }

        Category category = categoryRepo.findById(productCreateDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + productCreateDTO.getCategoryId()));

        Product product = new Product();
        product.setName(productCreateDTO.getName());
        product.setBrand(productCreateDTO.getBrand());
        product.setDescription(productCreateDTO.getDescription());
        product.setImgUrl(productCreateDTO.getImgUrl());
        product.setPrice(productCreateDTO.getPrice());
        product.setStockQuantity(productCreateDTO.getStockQuantity());
        product.setCategory(category);

        if (productCreateDTO.getSpecification() != null && !productCreateDTO.getSpecification().isEmpty()) {
            processProductSpecifications(product, productCreateDTO.getSpecification(), category.getId());
        }

        Product savedProduct = productRepo.save(product);

        Product productWithDetails = productRepo.findByIdWithDetails(savedProduct.getId())
                .orElseThrow(() -> new RuntimeException("Error fetching saved product details"));

        return ProductMapper.toResponse(productWithDetails);
    }

    private void processProductSpecifications(Product product, Map<String, String> specifications, Long categoryId) {
        List<SpecificationField> categoryFields = specificationFieldRepo.findByCategoryIdWithOptions(categoryId);

        for (Map.Entry<String, String> entry : specifications.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();

            if (fieldValue == null || fieldValue.trim().isEmpty()) {
                continue;
            }

            SpecificationField specField = categoryFields.stream()
                    .filter(field -> field.getFieldName().equalsIgnoreCase(fieldName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                            "Specification field '" + fieldName + "' not found for category ID: " + categoryId
                    ));

            if (specField.getFieldType() == FieldType.DROPDOWN) {
                validateDropdownOption(specField, fieldValue);
            }

            ProductSpecification productSpec = new ProductSpecification();
            productSpec.setProduct(product);
            productSpec.setSpecificationField(specField);
            productSpec.setValue(fieldValue.trim());

            product.addProductSpecification(productSpec);
        }
    }

    private void validateDropdownOption(SpecificationField specField, String value) {
        boolean isValidOption = specField.getOptions().stream()
                .anyMatch(option -> option.getValue().equalsIgnoreCase(value));

        if (!isValidOption) {
            throw new RuntimeException(
                    "Invalid value '" + value + "' for field '" + specField.getFieldName() +
                            "'. Valid options are: " +
                            specField.getOptions().stream()
                                    .map(option -> option.getValue())
                                    .collect(Collectors.joining(", "))
            );
        }
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepo.findAllWithDetails();
        return ProductMapper.toResponseList(products);
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepo.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return ProductMapper.toResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {
        if (!categoryRepo.existsById(categoryId)) {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }

        List<Product> products = productRepo.findByCategoryId(categoryId);
        return ProductMapper.toResponseList(products);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsByCategoryName(String categoryName) {
        List<Product> products = productRepo.findByCategoryName(categoryName);
        return ProductMapper.toResponseList(products);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> searchProducts(String searchTerm) {
        List<Product> products = productRepo.findByNameContainingIgnoreCase(searchTerm);
        return ProductMapper.toResponseList(products);
    }

    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepo.deleteById(id);
    }

    public ProductResponseDTO updateProductStock(Long id, int newStockQuantity) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setStockQuantity(newStockQuantity);
        Product updatedProduct = productRepo.save(product);

        return ProductMapper.toResponse(updatedProduct);
    }
}