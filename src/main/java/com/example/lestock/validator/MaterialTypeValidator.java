package com.example.lestock.validator;
import com.example.lestock.controller.dto.MaterialTypeDTO;
import com.example.lestock.controller.mapper.MaterialTypeMapper;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.model.MaterialType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
@RequiredArgsConstructor
public class MaterialTypeValidator implements ConstraintValidator<ValidMaterialType, MaterialTypeDTO> {
    private final MaterialTypeMapper materialTypeMapper;
    private final MaterialTypeDAO materialTypeDAO;
    @Override
    public boolean isValid(MaterialTypeDTO materialTypeDTO, ConstraintValidatorContext context) {
        if(materialTypeDTO == null) return true;
        boolean valid = true;
        context.disableDefaultConstraintViolation();

        if(isMaterialTypeSaved(materialTypeDTO)) {
            context.buildConstraintViolationWithTemplate("Material type already created!")
                    .addPropertyNode("name")
                    .addConstraintViolation();
            valid = false;
        }
        return valid;
    }

    public Boolean isMaterialTypeSaved(MaterialTypeDTO materialTypeDTO) {
        MaterialType materialType = materialTypeMapper.toEntity(materialTypeDTO);
        Optional<MaterialType> materialTypeOptional = materialTypeDAO.findByName(materialTypeDTO.name());

        if (materialType.getId() == null) {
            return materialTypeOptional.isPresent();
        }

        return materialTypeOptional
                .map(MaterialType::getId)
                .stream()
                .anyMatch(
                        id -> !id.equals(materialType.getId())
                );

    }
}
