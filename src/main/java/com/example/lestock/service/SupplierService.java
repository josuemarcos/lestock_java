package com.example.lestock.service;
import com.example.lestock.dao.SupplierDAO;
import com.example.lestock.model.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierDAO supplierDAO;

    public List<Supplier> getSuppliers() {
        return supplierDAO.findAll();
    }

    public void saveSupplier(Supplier supplier) {
        supplierDAO.save(supplier);
    }
}
