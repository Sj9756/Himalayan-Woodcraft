package com.himalayanwc.products.Dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private String imgUrl;
    private BigDecimal price;
    private int stockQuantity;
    private CategoryResponseDTO category;
    private List<ProductSpecificationResponseDTO> specifications = new ArrayList<>();
}

