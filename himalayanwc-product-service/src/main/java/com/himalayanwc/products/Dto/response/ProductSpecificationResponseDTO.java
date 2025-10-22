package com.himalayanwc.products.Dto.response;

import lombok.Data;

@Data
public class ProductSpecificationResponseDTO {
    private Long id;
    private String fieldName;
    private String value;
    private String fieldType;
}
