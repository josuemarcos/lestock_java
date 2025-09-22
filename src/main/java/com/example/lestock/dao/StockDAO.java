package com.example.lestock.dao;

import com.example.lestock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDAO extends JpaRepository<Stock, Long> {
}
