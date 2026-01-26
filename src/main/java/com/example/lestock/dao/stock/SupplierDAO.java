package com.example.lestock.dao.stock;
import com.example.lestock.model.stock.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierDAO extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByName(String name);
}
