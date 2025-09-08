package com.example.lestock.validator;
import com.example.lestock.controller.dto.SupplierDTO;
import com.example.lestock.controller.mapper.SupplierMapper;
import com.example.lestock.dao.SupplierDAO;
import com.example.lestock.model.Supplier;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class SupplierValidator implements ConstraintValidator<ValidSupplier, SupplierDTO> {
    private final SupplierDAO supplierDAO;
    private final SupplierMapper supplierMapper;

    @Override
    public boolean isValid(SupplierDTO supplierDTO, ConstraintValidatorContext context) {
        if(supplierDTO == null) return true;
        boolean valid = true;
        context.disableDefaultConstraintViolation();

        if(isSupplierSaved(supplierDTO)) {
            context.buildConstraintViolationWithTemplate("Supplier already created!")
                    .addPropertyNode("name")
                    .addConstraintViolation();
            valid = false;
        }
        return valid;

    }

    public Boolean isSupplierSaved(SupplierDTO supplierDTO) {
        Supplier supplierEntity = supplierMapper.toEntity(supplierDTO);
        Optional<Supplier> supplierOptional = supplierDAO.findByName(supplierDTO.name());

        if (supplierEntity.getId() == null) {
            return supplierOptional.isPresent();
        }

        return supplierOptional
                .map(Supplier::getId)
                .stream()
                .anyMatch(
                        id -> !id.equals(supplierEntity.getId())
                );

    }

}

