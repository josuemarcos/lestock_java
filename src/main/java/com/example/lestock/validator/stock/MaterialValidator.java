package com.example.lestock.validator.stock;
import com.example.lestock.controller.dto.stock.SaveMaterialDTO;
import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import com.example.lestock.dao.stock.MaterialDAO;
import com.example.lestock.dao.stock.MaterialTypeDAO;
import com.example.lestock.dao.stock.SupplierDAO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.model.stock.Material;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
            FieldErrorDTO fieldErrorSupplier = new FieldErrorDTO(
                    "IdSupplier",
                    "Material with supplier and material type pair already exists",
                    "UniquenessBreak"
            );
            FieldErrorDTO fieldErrorMaterialType = new FieldErrorDTO(
                    "IdMaterialType",
                    "Material with supplier and material type pair already exists",
                    "UniquenessBreak"
            );
            throw new DuplicateRecordException("Material already exists", List.of(fieldErrorSupplier, fieldErrorMaterialType));
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
