package com.example.lestock.dao;

import com.example.lestock.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialDAO extends JpaRepository<Material, Long> {
}
