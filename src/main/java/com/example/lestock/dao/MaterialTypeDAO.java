package com.example.lestock.dao;

import com.example.lestock.model.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialTypeDAO extends JpaRepository<MaterialType, Long> {
    Optional<MaterialType> findByNameAndBrand(String name, String brand);
}
