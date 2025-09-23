package com.example.lestock.validator;
import com.example.lestock.controller.dto.SaveMaterialDTO;
import com.example.lestock.dao.MaterialDAO;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.dao.SupplierDAO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.model.Material;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialValidator implements ConstraintValidator<ValidMaterial, SaveMaterialDTO> {
    private final MaterialDAO materialDAO;
    private final SupplierDAO supplierDAO;
    private final MaterialTypeDAO materialTypeDAO;

    @Override
    public boolean isValid(SaveMaterialDTO saveMaterialDTO, ConstraintValidatorContext context) {
        if(saveMaterialDTO == null) return true;
        boolean valid = true;
        context.disableDefaultConstraintViolation();

        if(supplierNotRegistered(saveMaterialDTO)) {
            context.buildConstraintViolationWithTemplate("Invalid Supplier")
                    .addPropertyNode("IdSupplier")
                    .addConstraintViolation();
            valid = false;
        }

        if(materialTypeNotRegistered(saveMaterialDTO)) {
            context.buildConstraintViolationWithTemplate("Invalid Material Type")
                    .addPropertyNode("IdMaterialType")
                    .addConstraintViolation();
            valid = false;
        }
        return valid;
    }
    private Boolean supplierNotRegistered(SaveMaterialDTO materialDTO) {
        if(materialDTO.IdSupplier() == null) return false;
        return supplierDAO.findById(materialDTO.IdSupplier()).isEmpty();
    }
    private Boolean materialTypeNotRegistered(SaveMaterialDTO materialDTO) {
        if(materialDTO.IdMaterialType() == null) return false;
        return materialTypeDAO.findById(materialDTO.IdMaterialType()).isEmpty();
    }

    public void validateMaterial(Material material) {
        if(isMaterialSaved(material)) {
            throw new DuplicateRecordException("Material already exists");
        }
    }

    private Boolean isMaterialSaved(Material material) {
        Optional<Material> materialOptional = materialDAO.findBySupplierAndMaterialType(
                material.getSupplier(), material.getMaterialType());

        if (material.getId() == null) {
            return materialOptional.isPresent();
        }
        return materialOptional
                .map(Material::getId)
                .stream()
                .anyMatch(
                        id -> !id.equals(material.getId())
                );
    }
}
