package com.himalayanwc.products.services;

import com.himalayanwc.products.Dto.create.CategoryCreateDTO;
import com.himalayanwc.products.Dto.response.CategoryResponseDTO;
import com.himalayanwc.products.mapper.CategoryMapper;
import com.himalayanwc.products.model.Category;
import com.himalayanwc.products.model.SpecificationField;
import com.himalayanwc.products.model.SpecificationOption;
import com.himalayanwc.products.repo.CategoryRepo;
import com.himalayanwc.products.repo.SpecificationFieldRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepo categoryRepo;
    private final SpecificationFieldRepo specificationFieldRepo;

    public CategoryService(CategoryRepo categoryRepo, SpecificationFieldRepo specificationFieldRepo) {
        this.categoryRepo = categoryRepo;
        this.specificationFieldRepo = specificationFieldRepo;
    }

    public CategoryResponseDTO createCategory(CategoryCreateDTO categoryCreateDTO) {
        if (categoryRepo.existsByName(categoryCreateDTO.getName())) {
            throw new RuntimeException("Category with name '" + categoryCreateDTO.getName() + "' already exists");
        }

        Category category = CategoryMapper.toEntity(categoryCreateDTO);
        setupBidirectionalRelationships(category);
        Category savedCategory = categoryRepo.save(category);
        return CategoryMapper.toResponse(savedCategory);
    }

    // ✅ FIXED: Load category with specifications in separate steps
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryByIdWithSpecifications(Long id) {
        // First, get category with specification fields
        Category category = categoryRepo.findByIdWithSpecificationFields(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        // Then, manually load options for each specification field
        if (category.getSpecificationFields() != null) {
            for (SpecificationField field : category.getSpecificationFields()) {
                // This will trigger lazy loading within transaction
                field.getOptions().size(); // Force initialization
            }
        }

        return CategoryMapper.toResponse(category);
    }

    // ✅ Get all categories with specifications
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAllCategoriesWithSpecifications() {
        List<Category> categories = categoryRepo.findAllWithSpecificationFields();

        // Manually load options for each category
        for (Category category : categories) {
            if (category.getSpecificationFields() != null) {
                for (SpecificationField field : category.getSpecificationFields()) {
                    field.getOptions().size(); // Force initialization
                }
            }
        }

        return categories.stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Get all categories (basic - without specifications)
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Get category by ID (basic - without specifications)
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return CategoryMapper.toResponse(category);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepo.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepo.deleteById(id);
    }

    private void setupBidirectionalRelationships(Category category) {
        if (category.getSpecificationFields() != null) {
            for (SpecificationField field : category.getSpecificationFields()) {
                field.setCategory(category);

                if (field.getOptions() != null) {
                    for (SpecificationOption option : field.getOptions()) {
                        option.setSpecificationField(field);
                    }
                }
            }
        }
    }
}