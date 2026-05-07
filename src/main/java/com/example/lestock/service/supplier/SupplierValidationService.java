package com.example.lestock.service.supplier;

import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import com.example.lestock.dao.stock.SupplierDAO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.exceptions.ResourceNotFoundException;
import com.example.lestock.model.stock.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierValidationService {
    private final SupplierDAO supplierDAO;

    public void validateSupplier(Supplier supplier) {
        if(isSupplierSaved(supplier)) {
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(
                    "name",
                    "Supplier already exists",
                    "UniquenessBreak"
            );
            throw new DuplicateRecordException("Supplier already exists", List.of(fieldErrorDTO));
        }
    }

    private Boolean isSupplierSaved(Supplier supplier) {
        Optional<Supplier> supplierOptional = supplierDAO.findByName(supplier.getName());

        if (supplier.getId() == null) {
            return supplierOptional.isPresent();
        }
        return supplierOptional
                .map(Supplier::getId)
                .stream()
                .anyMatch(
                        id -> !id.equals(supplier.getId())
                );
    }

    public Supplier getExistingSupplier(Long supplierId) {
        return supplierDAO.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + supplierId + " not found"));
    }
}
