package com.example.lestock.dao.product;

import com.example.lestock.model.product.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostDAO extends JpaRepository<Cost, Long> {
    Optional<Cost> findByName(String name);
}
