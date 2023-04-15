package com.softcaribbean.hulkstore.api.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.softcaribbean.hulkstore.api.models.enums.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ProductEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7751769765179188953L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "total_pieces", nullable = false)
    private int totalPieces;

    @Column(name = "create_at", nullable = false)
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<ProductStockEntity> transactions;

    @PrePersist
    private void prePersist() {
        this.setCreatedAt(ZonedDateTime.now());
    }
}
