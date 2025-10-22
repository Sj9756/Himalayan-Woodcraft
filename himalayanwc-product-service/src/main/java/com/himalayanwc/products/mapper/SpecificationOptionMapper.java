package com.himalayanwc.products.mapper;

import com.himalayanwc.products.Dto.create.SpecificationOptionCreateDTO;
import com.himalayanwc.products.Dto.response.SpecificationOptionResponseDTO;
import com.himalayanwc.products.model.SpecificationOption;
import java.util.ArrayList;
import java.util.List;

public class SpecificationOptionMapper {
    public static SpecificationOption toEntity(SpecificationOptionCreateDTO specificationOptionCreateDTO) {
        SpecificationOption specificationOption = new SpecificationOption();
        specificationOption.setValue(specificationOptionCreateDTO.getValue());
        return specificationOption;
    }

    public static List<SpecificationOption> toEntityList(List<SpecificationOptionCreateDTO> specificationOptionCreateDTOList) {
        if (specificationOptionCreateDTOList == null) {
            return new ArrayList<>();
        }

        List<SpecificationOption> specificationOptionList = new ArrayList<>();
        for (SpecificationOptionCreateDTO specificationOptionCreateDTO : specificationOptionCreateDTOList) {
            SpecificationOption specificationOption = toEntity(specificationOptionCreateDTO);
            specificationOptionList.add(specificationOption);
        }
        return specificationOptionList;
    }

    public static SpecificationOptionResponseDTO toResponse(SpecificationOption specificationOption) {
        SpecificationOptionResponseDTO specificationOptionResponseDTO = new SpecificationOptionResponseDTO();
        specificationOptionResponseDTO.setId(specificationOption.getId());
        specificationOptionResponseDTO.setValue(specificationOption.getValue());
        return specificationOptionResponseDTO;
    }

    public static List<SpecificationOptionResponseDTO> toResponseList(List<SpecificationOption> specificationOptionList) {
        if (specificationOptionList == null) {
            return new ArrayList<>();
        }

        List<SpecificationOptionResponseDTO> specificationOptionResponseDTOList = new ArrayList<>();
        for (SpecificationOption specificationOption : specificationOptionList) {
            SpecificationOptionResponseDTO specificationOptionResponseDTO = toResponse(specificationOption);
            specificationOptionResponseDTOList.add(specificationOptionResponseDTO);
        }
        return specificationOptionResponseDTOList;
    }
}