package com.himalayanwc.products.mapper;

import com.himalayanwc.products.Dto.create.SpecificationFieldCreateDTO;
import com.himalayanwc.products.Dto.response.SpecificationFieldResponseDTO;
import com.himalayanwc.products.model.SpecificationField;
import java.util.ArrayList;
import java.util.List;

public class SpecificationFieldMapper {
    public static SpecificationField toEntity(SpecificationFieldCreateDTO dto){
        SpecificationField specificationField = new SpecificationField();
        specificationField.setFieldName(dto.getFieldName());
        specificationField.setFieldType(dto.getFieldType());

        if (dto.getOptions() != null) {
            specificationField.setOptions(SpecificationOptionMapper.toEntityList(dto.getOptions()));
        }

        return specificationField;
    }

    public static List<SpecificationField> toEntityList(List<SpecificationFieldCreateDTO> specificationFieldCreateDTOS) {
        if (specificationFieldCreateDTOS == null) {
            return new ArrayList<>();
        }

        List<SpecificationField> specificationFieldList = new ArrayList<>();
        for (SpecificationFieldCreateDTO specificationFieldCreateDTO : specificationFieldCreateDTOS) {
            SpecificationField specificationField = toEntity(specificationFieldCreateDTO);
            specificationFieldList.add(specificationField);
        }
        return specificationFieldList;
    }

    public static SpecificationFieldResponseDTO toResponse(SpecificationField specificationField) {
        SpecificationFieldResponseDTO specificationFieldResponseDTO = new SpecificationFieldResponseDTO();
        specificationFieldResponseDTO.setId(specificationField.getId());
        specificationFieldResponseDTO.setFieldName(specificationField.getFieldName());
        specificationFieldResponseDTO.setFieldType(specificationField.getFieldType());

        if (specificationField.getOptions() != null) {
            specificationFieldResponseDTO.setSpecificationOptionResponseDTOS(
                    SpecificationOptionMapper.toResponseList(specificationField.getOptions())
            );
        }

        return specificationFieldResponseDTO;
    }

    public static List<SpecificationFieldResponseDTO> toResponseList(List<SpecificationField> specificationFields) {
        if (specificationFields == null) {
            return new ArrayList<>();
        }

        List<SpecificationFieldResponseDTO> specificationFieldResponseDTOList = new ArrayList<>();
        for (SpecificationField field : specificationFields) {
            SpecificationFieldResponseDTO specificationFieldResponseDTO = toResponse(field);
            specificationFieldResponseDTOList.add(specificationFieldResponseDTO);
        }
        return specificationFieldResponseDTOList;
    }
}