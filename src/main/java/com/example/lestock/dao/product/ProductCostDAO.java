package com.example.lestock.dao.product;

import com.example.lestock.model.product.ProductCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCostDAO extends JpaRepository<ProductCost, Long> {
}
