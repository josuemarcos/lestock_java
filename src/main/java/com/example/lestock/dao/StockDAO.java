package com.example.lestock.dao;

import com.example.lestock.model.Material;
import com.example.lestock.model.MaterialType;
import com.example.lestock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockDAO extends JpaRepository<Stock, Long> {
    Optional<Stock> findByMaterialType(MaterialType materialType);
}
