package com.example.lestock.model.product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_costs")
@Getter
@Setter
@RequiredArgsConstructor
public class ProductCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cost_id", nullable = false)
    private Cost cost;

    @Column(nullable = false)
    private Double quantity;

    @Transient
    public Double getTotalCost() {
        if(cost == null || cost.getUnitPrice() == null) return 0.0;
        return quantity * cost.getUnitPrice();
    }
}
