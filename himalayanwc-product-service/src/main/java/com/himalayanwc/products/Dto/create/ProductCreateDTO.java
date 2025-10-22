package com.himalayanwc.products.Dto.create;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ProductCreateDTO {
    private String name;
    private String brand;
    private String description;
    private String imgUrl;
    private BigDecimal price;
    private int stockQuantity;
    private Long categoryId;
    private Map<String, String> specification;
}