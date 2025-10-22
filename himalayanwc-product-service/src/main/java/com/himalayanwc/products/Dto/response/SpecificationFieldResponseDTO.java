package com.himalayanwc.products.Dto.response;

import com.himalayanwc.products.model.Category;
import com.himalayanwc.products.model.FieldType;
import com.himalayanwc.products.model.SpecificationOption;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpecificationFieldResponseDTO {
    private Long id;
    private String fieldName;
    private FieldType fieldType;
    private List<SpecificationOptionResponseDTO> specificationOptionResponseDTOS = new ArrayList<>();
}
