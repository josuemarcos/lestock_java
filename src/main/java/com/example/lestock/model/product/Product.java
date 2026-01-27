package com.example.lestock.model.product;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Double price;

    @Column(name = "profit_margin", nullable = false)
    private Double profitMargin;

    @OneToMany(mappedBy = "product")
    private Set<ProductCost> productCosts =  new HashSet<>();

}
