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
@Entity
@Table
@Getter
@Setter
@ToString(exclude = {"materials"})
@EntityListeners(AuditingEntityListener.class)
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String contact;

    @Column(name = "social_media")
    private String socialMedia;

    @Column
    private String address;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @OneToMany(mappedBy = "supplier")
    private List<Material> materials;

    @Column(name = "user_id")
    private Long userId;

    public Supplier() {
    }

    public Supplier(String name, String description, String contact, String socialMedia, String address) {
        this.name = name;
        this.description = description;
        this.contact = contact;
        this.socialMedia = socialMedia;
        this.address = address;
    }
}
