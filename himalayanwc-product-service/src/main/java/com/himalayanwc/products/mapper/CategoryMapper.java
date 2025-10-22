package com.himalayanwc.products.mapper;

import com.himalayanwc.products.Dto.create.CategoryCreateDTO;
import com.himalayanwc.products.Dto.response.CategoryResponseDTO;
import com.himalayanwc.products.model.Category;

public class CategoryMapper {
    public static Category toEntity(CategoryCreateDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        if (dto.getSpecificationFieldCreateDTOS() != null) {
            category.setSpecificationFields(SpecificationFieldMapper.toEntityList(dto.getSpecificationFieldCreateDTOS()));
        }

        return category;
    }

    public static CategoryResponseDTO toResponse(Category category) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setDescription(category.getDescription()); // ADD THIS LINE

        if (category.getSpecificationFields() != null) {
            categoryResponseDTO.setSpecificationFieldResponseDTOS(
                    SpecificationFieldMapper.toResponseList(category.getSpecificationFields())
            );
        }

        return categoryResponseDTO;
    }
}