package com.example.lestock.dao.stock;

import com.example.lestock.model.stock.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialTypeDAO extends JpaRepository<MaterialType, Long> {
    Optional<MaterialType> findByNameAndBrand(String name, String brand);
}
