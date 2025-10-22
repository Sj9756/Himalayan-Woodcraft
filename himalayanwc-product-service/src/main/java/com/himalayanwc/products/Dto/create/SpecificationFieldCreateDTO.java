package com.himalayanwc.products.Dto.create;

import com.himalayanwc.products.model.FieldType;
import com.himalayanwc.products.model.SpecificationOption;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpecificationFieldCreateDTO {
    private String fieldName;
    private FieldType fieldType;
    private List<SpecificationOptionCreateDTO> options = new ArrayList<>();

}
