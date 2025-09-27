package com.example.lestock.dao;

import com.example.lestock.model.MaterialType;
import com.example.lestock.model.Stock;
import com.example.lestock.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementDAO extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByStock(Stock stock);
}
