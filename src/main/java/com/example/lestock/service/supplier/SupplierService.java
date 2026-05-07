package com.example.lestock.service.supplier;
import com.example.lestock.controller.dto.stock.SaveSupplierDTO;
import com.example.lestock.controller.mapper.stock.SupplierMapper;
import com.example.lestock.dao.stock.SupplierDAO;
import com.example.lestock.model.stock.Supplier;
import com.example.lestock.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierDAO supplierDAO;
    private final SupplierValidationService validateSupplierService;
    private final SupplierMapper supplierMapper;

    public List<Supplier> getSuppliers() {
        return supplierDAO.findAll();
    }

    public Supplier saveSupplier(Supplier supplier, User loggedUser) {
        validateSupplierService.validateSupplier(supplier);
        supplier.setUserId(loggedUser.getId());
        return supplierDAO.save(supplier);
    }

    public Supplier getSupplierById(Long supplierId) {
        return validateSupplierService.getExistingSupplier(supplierId);
    }

    public void updateSupplier(Long supplierId, SaveSupplierDTO updatedSupplier, User loggedUser) {
        Supplier supplier = validateSupplierService.getExistingSupplier(supplierId);
        supplierMapper.updateEntity(supplier, updatedSupplier);
        validateSupplierService.validateSupplier(supplier);
        supplierDAO.save(supplier);
    }

    public void deleteSupplier(Long supplierId) {
        Supplier supplier = validateSupplierService.getExistingSupplier(supplierId);
        supplierDAO.delete(supplier);
    }
}
