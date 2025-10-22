package com.himalayanwc.products.Dto.create;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryCreateDTO {
    private String name;
    private String description;
    private List<SpecificationFieldCreateDTO> specificationFieldCreateDTOS=new ArrayList<>();
}
