package com.example.lestock.model.product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "profit_margin", nullable = false)
    private Double profitMargin;

    @OneToMany(mappedBy = "product")
    private Set<ProductCost> productCosts =  new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductMaterial> productMaterials =  new HashSet<>();

    @Transient
    public Double getOperationalCost() {
        return this.productCosts.stream()
                .mapToDouble(pc -> pc.getCost().getUnitPrice() * pc.getQuantity())
                .sum();
    }

    @Transient
    public Double getMaterialCost() {
        return this.productMaterials.stream()
                .mapToDouble(pc -> pc.getMaterial().getPricePerAmount() * pc.getQuantity())
                .sum();
    }

    @Transient
    public Double getPrice() {
        return (this.getOperationalCost() + this.getMaterialCost()) * this.profitMargin;
    }

}
