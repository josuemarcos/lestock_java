package com.example.lestock.dao;

import com.example.lestock.model.Material;
import com.example.lestock.model.MaterialType;
import com.example.lestock.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialDAO extends JpaRepository<Material, Long> {
    Optional<Material> findBySupplierAndMaterialType(Supplier supplier, MaterialType materialType);
}
