package com.example.lestock.model.stock;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Builder @NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Float currentQuantity;

    @Column
    private Float averageCost;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Builder.Default
    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockMovement> stockMovements = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "material_type_id")
    private MaterialType materialType;

    @Column(name = "user_id")
    private Long userId;

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;

        if (materialType != null && materialType.getStock() != this) {
            materialType.setStock(this);
        }
    }

    public void addStockMovement(StockMovement stockMovement) {
        this.stockMovements.add(stockMovement);

        if(stockMovement != null && stockMovement.getStock() != this) {
            stockMovement.setStock(this);
        }
    }

    public void removeStockMovement(StockMovement stockMovement) {
        this.stockMovements.remove(stockMovement);
    }

    public Float getCurrentQuantity() {
        float incoming = stockMovements.stream()
                .filter(Objects::nonNull)
                .filter(sm -> sm.getMovementType().equals(MovementType.PURCHASE))
                .map(StockMovement::getQuantity)
                .reduce(0f, Float::sum);

        float withdrawals = stockMovements.stream()
                .filter(Objects::nonNull)
                .filter(sm -> sm.getMovementType().equals(MovementType.SALE))
                .map(StockMovement::getQuantity)
                .reduce(0f, Float::sum);

        return incoming -  withdrawals;
    }

    public Float getAverageCost() {
        int size = stockMovements.size();

        List<StockMovement> lastPurchases = stockMovements.subList(
                        Math.max(0, size - 10),
                        size
                ).stream()
                .filter(Objects::nonNull)
                .filter(sm -> sm.getMovementType().equals(MovementType.PURCHASE))
                .toList();

        float totalAmount = 0f;
        float totalPrice = 0f;

        for (StockMovement sm : lastPurchases) {
            totalAmount += sm.getQuantity();
            totalPrice += sm.getMovementTotalPrice();
        }

        return totalAmount == 0 ? null : totalPrice / totalAmount;
    }
}
