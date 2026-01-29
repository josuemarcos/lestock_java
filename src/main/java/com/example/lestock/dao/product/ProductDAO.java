package com.example.lestock.dao.product;
import com.example.lestock.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Long> {
}
