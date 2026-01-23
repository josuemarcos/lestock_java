package com.example.lestock.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "material_type")
@Getter
@Setter
@ToString(exclude = {"materials"})
@EntityListeners(AuditingEntityListener.class)
public class MaterialType
{
    public MaterialType(String name, String metricUnit, String brand) {
        this.name = name;
        this.metricUnit = metricUnit;
        this.brand = brand;
    }

    public MaterialType() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "metric_unit", nullable = false, length = 100)
    private String metricUnit;

    @Column(length = 100)
    private String brand;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @OneToMany(mappedBy = "materialType")
    private List<Material> materials;

    @OneToOne(mappedBy = "materialType")
    private Stock stock;

    @Column(name = "user_id")
    private Long userId;
}
