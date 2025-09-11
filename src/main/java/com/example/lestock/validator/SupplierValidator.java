package com.example.lestock.validator;
import com.example.lestock.dao.SupplierDAO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.model.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierValidator  {
    private final SupplierDAO supplierDAO;

    public void isSupplierValid(Supplier supplier) {
        if(isSupplierSaved(supplier)) {
            throw new DuplicateRecordException("Supplier already exists");
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

}

