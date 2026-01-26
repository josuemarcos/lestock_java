package com.example.lestock.dao.stock;

import com.example.lestock.model.stock.Stock;
import com.example.lestock.model.stock.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementDAO extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByStock(Stock stock);
}
