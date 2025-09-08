package com.example.lestock.dao;
import com.example.lestock.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierDAO extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByName(String name);
}
