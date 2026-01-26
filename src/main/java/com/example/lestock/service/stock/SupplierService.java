package com.example.lestock.service.stock;
import com.example.lestock.dao.stock.SupplierDAO;
import com.example.lestock.model.stock.Supplier;
import com.example.lestock.model.User;
import com.example.lestock.security.SecurityService;
import com.example.lestock.validator.stock.SupplierValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierDAO supplierDAO;
    private final SupplierValidator supplierValidator;
    private final SecurityService securityService;

    public List<Supplier> getSuppliers() {
        return supplierDAO.findAll();
    }

    public void saveSupplier(Supplier supplier) {
        supplierValidator.validateSupplier(supplier);
        User user = securityService.getLoggedUser();
        supplier.setUserId(user.getId());
        supplierDAO.save(supplier);
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierDAO.findById(id);
    }

    public void updateSupplier(Supplier supplier) {
        supplierValidator.validateSupplier(supplier);
        User user = securityService.getLoggedUser();
        supplier.setUserId(user.getId());
        supplierDAO.save(supplier);
    }

    public void deleteSupplier(Supplier supplier) {
        supplierDAO.delete(supplier);
    }
}
