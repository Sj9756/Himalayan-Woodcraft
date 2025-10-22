package com.himalayanwc.products.controller;

import com.himalayanwc.products.Dto.create.CategoryCreateDTO;
import com.himalayanwc.products.Dto.response.CategoryResponseDTO;
import com.himalayanwc.products.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@CrossOrigin("*")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public CategoryResponseDTO addCategory(@RequestBody CategoryCreateDTO categoryCreateDTO) {
        return categoryService.createCategory(categoryCreateDTO);
    }

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/with-specifications")
    public List<CategoryResponseDTO> getAllCategoriesWithSpecifications() {
        return categoryService.getAllCategoriesWithSpecifications();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/{id}/with-specifications")
    public CategoryResponseDTO getCategoryByIdWithSpecifications(@PathVariable Long id) {
        return categoryService.getCategoryByIdWithSpecifications(id);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "Category deleted successfully";
    }
}