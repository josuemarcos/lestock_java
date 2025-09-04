package com.example.lestock.model;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@EntityListeners(AuditingEntityListener.class)
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "minimum_purchase_amount")
    private Float minimumPurchaseAmount;

    @Column
    private Float price;

    @Column
    private String description;

    @Column
    private String brand;

    @Column(name = "average_delivery_time")
    private Integer averageDeliveryTime;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "material_type_id")
    private MaterialType materialType;

    @Transient
    public Float getPricePerAmount() {
        if(price == null || minimumPurchaseAmount == null) return null;
        return price/ minimumPurchaseAmount;
    }

}
