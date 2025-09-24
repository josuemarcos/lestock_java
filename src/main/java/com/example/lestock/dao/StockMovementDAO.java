package com.example.lestock.dao;

import com.example.lestock.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementDAO extends JpaRepository<StockMovement, Long> {
}
