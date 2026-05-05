package com.example.lestock.model.stock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
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

    @OneToMany(mappedBy = "stock")
    private List<StockMovement> stockMovements;

    @OneToOne
    @JoinColumn(name = "material_type_id")
    private MaterialType materialType;

    @Column(name = "user_id")
    private Long userId;

    public void addStockMovement(StockMovement stockMovement) {
        this.stockMovements.add(stockMovement);
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
