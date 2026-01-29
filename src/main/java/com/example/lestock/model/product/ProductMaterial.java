package com.example.lestock.model.product;
import com.example.lestock.model.stock.Material;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_materials")
@Getter
@Setter
public class ProductMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(nullable = false)
    private Double quantity;

    @Transient
    public Double getTotalCost() {
        if(material == null || material.getPricePerAmount() == null) return 0.0;
        return quantity * material.getPricePerAmount();
    }
}
