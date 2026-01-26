package com.example.lestock.dao.stock;

import com.example.lestock.model.stock.Material;
import com.example.lestock.model.stock.MaterialType;
import com.example.lestock.model.stock.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialDAO extends JpaRepository<Material, Long> {
    Optional<Material> findBySupplierAndMaterialType(Supplier supplier, MaterialType materialType);
}
