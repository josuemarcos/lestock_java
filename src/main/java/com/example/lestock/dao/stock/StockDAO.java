package com.example.lestock.dao.stock;

import com.example.lestock.model.stock.MaterialType;
import com.example.lestock.model.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockDAO extends JpaRepository<Stock, Long> {
    Optional<Stock> findByMaterialType(MaterialType materialType);
}
