package com.example.lestock.dao;
import com.example.lestock.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierDAO extends JpaRepository<Supplier, Long> {
}
